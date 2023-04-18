package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.dto.ActivityDto;

/**
 * @author xukai
 * @create 2023-04-17 14:41
 */
public interface ActivityService {

    /**
     * 用户发布活动
     * @param userId 用户id
     * @param activityDto 活动信息参数
     * @return 活动id
     */
    int publishActivity(Integer userId, ActivityDto activityDto);

    /**
     * 用户修改活动信息
     * @param userId 用户id
     * @param activityDto 活动信息参数
     * @return 活动id
     */
    Long updateActivity(Integer userId, ActivityDto activityDto);
}
