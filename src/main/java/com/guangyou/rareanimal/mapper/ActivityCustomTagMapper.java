package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.ActivityCustomTag;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xukai
 * @create 2023-04-12 21:40
 */
@Mapper
public interface ActivityCustomTagMapper extends BaseMapper<ActivityCustomTag> {

    /**
     * 根据活动id 获取活动标签结合
     * @param activityId 活动id
     * @return 活动标签集合
     */
    List<String> getTagById(Long activityId);

}
