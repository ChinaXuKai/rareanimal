package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.mapper.ActivityCustomTagMapper;
import com.guangyou.rareanimal.mapper.ActivityJoinMapper;
import com.guangyou.rareanimal.mapper.ActivityMapper;
import com.guangyou.rareanimal.pojo.Activity;
import com.guangyou.rareanimal.pojo.ActivityCustomTag;
import com.guangyou.rareanimal.pojo.ActivityJoin;
import com.guangyou.rareanimal.pojo.Opinion;
import com.guangyou.rareanimal.pojo.dto.ActivityDto;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.ActivityVo;
import com.guangyou.rareanimal.pojo.vo.OpinionVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;
import com.guangyou.rareanimal.service.ActivityService;
import com.guangyou.rareanimal.utils.CopyUtils;
import com.guangyou.rareanimal.utils.ShiroUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author xukai
 * @create 2023-04-17 14:41
 */
@Slf4j
@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityCustomTagMapper activityCustomTagMapper;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @SneakyThrows
    @Transactional
    @Override
    public Long publishActivity(Integer userId, ActivityDto activityDto) {
        //1、先 添加到 t_activity 表中，获取到 活动id
        Activity activity = new Activity();
        activity.setActivityTitle( activityDto.getActivityTitle());
        activity.setPublishUid(userId.longValue());
        activity.setActivityDescribe( activityDto.getActivityDescription());
        activity.setActivityPlace( activityDto.getActivityPlace());
        activity.setRequestTime( System.currentTimeMillis());
        activity.setStartTime( sdf.parse(activityDto.getStartTime()).getTime());
        activity.setEndTime( sdf.parse(activityDto.getEndTime()).getTime());
        activity.setUpdateTime(null);
        activity.setCoverUrl(activityDto.getCoverUrl());
        activity.setAuditState(Activity.WAIT_AUDIT);
        activity.setAuditTime(null);
        activity.setPeopleCeiling(activityDto.getPeopleCeiling());
        activityMapper.insert(activity);
        Long activityId = activity.getActivityId();
        //2、添加到 t_activity_custom_tag 表中
        List<String> tagList = activityDto.getActivityTags();
        for (String tag : tagList) {
            ActivityCustomTag activityCustomTag = new ActivityCustomTag();
            activityCustomTag.setActivityId(activityId);
            activityCustomTag.setTagDescribe(tag);
            activityCustomTagMapper.insert(activityCustomTag);
        }
        //3、添加到 t_activity_join 表中一条数据
        ActivityJoin activityJoin = new ActivityJoin();
        activityJoin.setUserId(userId.longValue());
        activityJoin.setActivityId(activityId);
        activityJoinMapper.insert(activityJoin);
        return activityId;
    }


    @SneakyThrows
    @Transactional
    @Override
    public Long updateActivity(Integer userId, ActivityDto activityDto) {
        Long activityId = activityDto.getActivityId();
        //1、根据 活动id 修改 t_activity 表
        LambdaUpdateWrapper<Activity> activityUpdateWrapper = new LambdaUpdateWrapper<>();
        activityUpdateWrapper.eq(Activity::getActivityId, activityId);
        Activity updateActivity = new Activity();
        updateActivity.setActivityTitle( activityDto.getActivityTitle());
        updateActivity.setPublishUid(userId.longValue());
        updateActivity.setActivityDescribe( activityDto.getActivityDescription());
        updateActivity.setActivityPlace( activityDto.getActivityPlace());
        updateActivity.setRequestTime(activityMapper.getActivityById(activityId).getRequestTime());
        updateActivity.setStartTime( sdf.parse(activityDto.getStartTime()).getTime());
        updateActivity.setEndTime( sdf.parse(activityDto.getEndTime()).getTime());
        updateActivity.setUpdateTime(System.currentTimeMillis());
        updateActivity.setCoverUrl(activityDto.getCoverUrl());
        updateActivity.setPeopleCeiling(activityDto.getPeopleCeiling());
        activityMapper.update(updateActivity, activityUpdateWrapper);
        //审核状态 和 审核时间 重新置为 待审核 和 null
        activityMapper.updateActivityAuditById(Activity.WAIT_AUDIT,activityId);
        //2、根据 活动id 删除 t_activity_custom_tag 表中数据，后添加 数据
        LambdaQueryWrapper<ActivityCustomTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityCustomTag::getActivityId,activityId );
        activityCustomTagMapper.delete(queryWrapper);
        List<String> activityTags = activityDto.getActivityTags();
        for (String tag : activityTags) {
            ActivityCustomTag customTag = new ActivityCustomTag();
            customTag.setActivityId(activityId);
            customTag.setTagDescribe(tag);
            activityCustomTagMapper.insert(customTag);
        }
        return activityId;
    }


    @Autowired
    private ActivityJoinMapper activityJoinMapper;

    @Override
    public Result joinActivityByUid(Long activityId) throws UnknownAccountException{
        //参与条件：活动参与人数上限未满、用户已登录、活动未截止、该用户还没报名该活动
        //未登录
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            return Result.fail(Result.FORBIDDEN,"你还未登录，还不能参与活动哦",null);
        }
            //根据活动id 获取到 活动信息
        Activity activity = activityMapper.selectById(activityId);
        //活动已截止
        if (System.currentTimeMillis() >= activity.getEndTime()){
            return Result.fail(Result.FORBIDDEN, "当前活动已截止，不能报名", null);
        }
        //用户已报名了该活动
        LambdaQueryWrapper<ActivityJoin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityJoin::getActivityId,activityId);
        queryWrapper.eq(ActivityJoin::getUserId, userId);
        if (activityJoinMapper.selectOne(queryWrapper) != null) {
            return Result.fail(Result.FORBIDDEN, "你已经报名了该活动，不能重复报名", null);
        }
        //活动参与人数上限已满
        int joinCount = activityJoinMapper.getJoinCountByActivityId(activityId);
        if (joinCount >= activity.getPeopleCeiling()){
            return Result.fail(Result.FORBIDDEN,"当前活动参与人数上限已满，不能报名",null);
        }

        ActivityJoin activityJoin = new ActivityJoin();
        activityJoin.setActivityId(activityId);
        activityJoin.setUserId(userId.longValue());
        int insertResult = activityJoinMapper.insert(activityJoin);
        if (insertResult != 0){
            return Result.succ(200, "你已成功报名当前活动", activityId);
        }
        return Result.fail("活动报名出现异常");
    }


    @Override
    public Result disJoinActivityByUid(Long activityId) {
        //取消参与条件：用户已登录、活动未截止
        //未登录
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            return Result.fail(Result.FORBIDDEN,"你还未登录，还不能取消参与活动哦",null);
        }
        //根据活动id 获取到 活动信息
        Activity activity = activityMapper.selectById(activityId);
        //活动已截止
        if (System.currentTimeMillis() >= activity.getEndTime()){
            return Result.fail(Result.FORBIDDEN, "当前活动已截止，不能取消报名", null);
        }

        LambdaQueryWrapper<ActivityJoin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ActivityJoin::getActivityId,activityId);
        queryWrapper.eq(ActivityJoin::getUserId,userId);
        int deleteResult = activityJoinMapper.delete(queryWrapper);
        if (deleteResult != 0){
            return Result.succ(200, "你已取消报名当前活动", activityId);
        }
        return Result.fail("取消活动报名出现异常");
    }


    @Autowired
    private CopyUtils copyUtils;
    @Override
    public PageDataVo<ActivityVo> getActivitiesByPage(PageDto pageDto) {
        PageDataVo<ActivityVo> pageDataVo = new PageDataVo<>();
        //前台给用户仅展示出 审核状态不为'待审核' 的活动
        List<Activity> activityList = activityMapper.getActivitiesByPage(Activity.WAIT_AUDIT,pageDto.getPageSize() * (pageDto.getPage() - 1), pageDto.getPageSize());
        pageDataVo.setPageData(copyUtils.activityListCopy(activityList));

        pageDataVo.setCurrent(pageDto.getPage());
        pageDataVo.setSize(pageDto.getPageSize());
        //前台给用户仅查询 审核状态不为'待审核' 的活动总数
        LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<Activity>();
        queryWrapper.ne(Activity::getAuditState, Activity.WAIT_AUDIT);
        int total = activityMapper.selectCount(queryWrapper).intValue();
        pageDataVo.setTotal(total);
        int isRemainZero = total%pageDto.getPageSize();
        if (isRemainZero != 0){
            pageDataVo.setPages( (total/pageDto.getPageSize()) + 1);
        }else {
            pageDataVo.setPages( total/pageDto.getPageSize() );
        }
        return pageDataVo;
    }

}
