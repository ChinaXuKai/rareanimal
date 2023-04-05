package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.dto.QuestionDto;

/**
 * @author xukai
 * @create 2023-03-31 21:28
 */
public interface QuestionAnswersService {

    /**
     * 用户发表问题
     * @param publishQuestionDto 发表问题需要的参数
     * @param userId 发表的用户的id
     * @return 发表问题 后该问题的 id
     */
    int publishQuestion(QuestionDto publishQuestionDto, Integer userId);

    /**
     * 用户修改问题
     * @param updateQuestionDto 修改问题需要的参数
     * @param userId 修改的用户的 id
     * @return 被修改的问题 id
     */
    int updateQuestion(QuestionDto updateQuestionDto, Integer userId);
}
