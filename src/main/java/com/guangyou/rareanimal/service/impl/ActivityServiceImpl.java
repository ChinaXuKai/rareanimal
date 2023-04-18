package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.guangyou.rareanimal.mapper.ActivityCustomTagMapper;
import com.guangyou.rareanimal.mapper.ActivityMapper;
import com.guangyou.rareanimal.pojo.Activity;
import com.guangyou.rareanimal.pojo.ActivityCustomTag;
import com.guangyou.rareanimal.pojo.dto.ActivityDto;
import com.guangyou.rareanimal.service.ActivityService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
        return activityId;
    }


    @SneakyThrows
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

}
