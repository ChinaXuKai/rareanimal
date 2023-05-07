package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-03-31 21:36
 */
@Repository
public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * 获取 用户id 发表的 问题分页数据集
     * @param userId 用户id
     * @param initialDataLocation 初始数据位置
     * @param pageSize 每页多少数据
     * @return 问题分页数据集
     */
    List<Question> getQuestionByPageAndUid(Integer userId, Integer initialDataLocation, Integer pageSize);

    /**
     * 按 时间降序 获取 问题分页数据集
     * @param initialDataLocation 初始数据位置
     * @param pageSize 每页多少数据
     * @return 问题分页数据集
     */
    List<Question> getQuestionOrderTimeByPage(Integer initialDataLocation, Integer pageSize);

    /**
     * 按热度排序：回答数从大到小 获取 问题分页数据集
     * @param initialDataLocation 初始数据位置
     * @param pageSize 每页多少数据
     * @return 问题分页数据集
     */
    List<Question> getQuestionOrderAnswersByPage(Integer initialDataLocation, Integer pageSize);

    /**
     * 按已完成筛选：筛选出已完成的，然后按时间从大到小
     * @param initialDataLocation 初始数据位置
     * @param pageSize 每页多少数据
     * @return 问题分页数据集
     */
    List<Question> getFinishQuestionByPage(Integer initialDataLocation, Integer pageSize);

    /**
     * 按紧急筛选：筛选出已完成的，然后按时间从大到小
     * @param initialDataLocation 初始数据位置
     * @param pageSize 每页多少数据
     * @return 问题分页数据集
     */
    List<Question> getUrgentQuestionByPage(Integer initialDataLocation, Integer pageSize);

}
