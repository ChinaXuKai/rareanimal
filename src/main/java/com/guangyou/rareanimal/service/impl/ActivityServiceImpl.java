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
import org.springframework.beans.BeanUtils;
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
    public int publishActivity(Integer userId, ActivityDto activityDto) {
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
        activity.setActivityCoverUrl(activityDto.getCoverUrl());
        activity.setAuditState(Activity.WAIT_AUDIT);
        activity.setAuditTime(null);
        Integer activityId = activityMapper.insert(activity);
        //2、添加到 t_activity_custom_tag 表中
        List<String> tagList = activityDto.getActivityTags();
        for (String tag : tagList) {
            ActivityCustomTag activityCustomTag = new ActivityCustomTag();
            activityCustomTag.setActivityId(activityId.longValue());
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
        updateActivity.setActivityCoverUrl(activityDto.getCoverUrl());
        //审核状态重新置为待审核
        updateActivity.setAuditState(Activity.WAIT_AUDIT);
        updateActivity.setAuditTime(null);
        activityMapper.update(updateActivity, activityUpdateWrapper);
        //2、根据 活动id 修改 t_activity_custom_tag 表中
        List<String> activityTags = activityDto.getActivityTags();
        for (String tag : activityTags) {
            activityCustomTagMapper.updateTagById(activityId,tag);
        }
        return activityId;
    }
}
