package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.Opinion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author xukai
 * @create 2023-04-13 9:40
 */
@Mapper
public interface OpinionMapper extends BaseMapper<Opinion> {

    /**
     * 根据意见id 查询用户意见
     * @param opinionId 意见id
     * @return 意见
     */
    Opinion getOpinionById(Long opinionId);

    /**
     * 获取 用户id 发表的 意见分页数据集
     * @param userId 用户id
     * @param initialDataLocation 初始数据位置
     * @param pageSize 每页多少数据
     * @return 意见分页数据集
     */
    List<Opinion> getOpinionsByPageAndUid(Integer userId, Integer initialDataLocation, Integer pageSize);

}
