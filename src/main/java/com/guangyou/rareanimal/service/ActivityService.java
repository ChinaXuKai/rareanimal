package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.ActivityDto;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.ActivityVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;

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
    Long publishActivity(Integer userId, ActivityDto activityDto);

    /**
     * 用户修改活动信息
     * @param userId 用户id
     * @param activityDto 活动信息参数
     * @return 活动id
     */
    Long updateActivity(Integer userId, ActivityDto activityDto);

    /**
     * 用户参与活动
     * @param activityId 活动id
     * @return 结果集
     */
    Result joinActivityByUid(Long activityId);

    /**
     * 用户取消报名活动
     * @param activityId 活动id
     * @return 结果集
     */
    Result disJoinActivityByUid(Long activityId);

    /**
     * 用户查看 活动分页数据集
     * @param pageDto 分页条件
     * @return 活动分页数据集
     */
    PageDataVo<ActivityVo> getActivitiesByPage(PageDto pageDto);
}
