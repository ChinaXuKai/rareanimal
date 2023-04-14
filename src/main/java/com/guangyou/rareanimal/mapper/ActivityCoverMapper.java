package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.ActivityCover;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xukai
 * @create 2023-04-12 21:40
 */
@Mapper
public interface ActivityCoverMapper extends BaseMapper<ActivityCover> {
    /**
     * 根据活动id 查询对应的活动封面集合
     * @param activityId 活动id
     * @return 活动封面集合
     */
    List<String> getActivityCoverById(Long activityId);
}
