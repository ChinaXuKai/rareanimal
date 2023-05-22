package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.KnowledgeQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-05-22 13:52
 */
@Repository
public interface KnowledgeQuestionMapper extends BaseMapper<KnowledgeQuestion> {

    /**
     * 获取问题 分页数据集
     * @param initialDataLocation 初始数据位置
     * @param pageSize 每页多少数据
     * @return 问题分页数据集
     */
    List<KnowledgeQuestion> getQuestionByPageAndType(Integer initialDataLocation, Integer pageSize, Long questionTypeId);
}
