package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xukai
 * @create 2023-04-12 21:40
 */
@Mapper
public interface ActivityMapper extends BaseMapper<Activity> {
    /**
     * 获取活动 分页数据集
     * @param auditState 被选取的审核状态
     * @param initialDataLocation 初始数据位置
     * @param pageSize 每页多少数据
     * @return 活动分页数据集
     */
    List<Activity> getActivitiesByPage(String auditState,Integer initialDataLocation, Integer pageSize);

    /**
     * 根据 活动id 查询到 活动
     * @param activityId 活动id
     * @return 活动
     */
    Activity getActivityById(Long activityId);

    /**
     * 根据 活动id 修改 活动审核
     * @param auditState 审核状态
     * @param activityId 活动id
     */
    void updateActivityAuditById(String auditState,Long activityId);
}
