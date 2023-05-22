package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.dto.SubmitOptionDto;

import java.util.List;

/**
 * @author xukai
 * @create 2023-05-20 11:19
 */
public interface KnowledgeQuestionService {

    /**
     * 根据 分页条件 和 问题类型 查询知识问题
     * @param pageDto 分页条件
     * @param questionTypeId 问题类型
     * @return 结果集
     */
    Result getQuestionByPageAndType(PageDto pageDto, Long questionTypeId);

    /**
     * 获取所有的问题类型
     * @return 问题类型集合
     */
    Result getAllQuestionType();

    /**
     * 根据 提交问题的dto集合 查询该用户 答对了几道题
     * @param submitOptionDtoList 提交问题的dto集合
     * @return 结果集
     */
    Result submitQuestionOption(List<SubmitOptionDto> submitOptionDtoList);

}
