package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.ActivityJoin;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xukai
 * @create 2023-04-12 21:41
 */
@Mapper
public interface ActivityJoinMapper extends BaseMapper<ActivityJoin> {
    /**
     * 获取 活动id 对应的 已报名/参与人数
     * @param activityId
     * @return
     */
    int getJoinCountByActivityId(Long activityId);
}
