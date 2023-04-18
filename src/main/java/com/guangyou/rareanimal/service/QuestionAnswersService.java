package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.dto.AnswerDto;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.dto.QuestionDto;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;
import com.guangyou.rareanimal.pojo.vo.QuestionVo;

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

    /**
     * 用户回答问题
     * @param answerDto 回答问题所需参数
     * @param userId 用户id
     * @return 回答id
     */
    int replyQuestion(AnswerDto answerDto, Integer userId);


    /**
     * 用户分页查询自己发表的问题
     * @param pageDto
     * @param userId
     * @return
     */
    PageDataVo<QuestionVo> getMyQuestionsByPage(PageDto pageDto, Integer userId);

//    /**
//     * 用户分页查看问题
//     * @param pageDto
//     * @return
//     */
//    PageDataVo<QuestionVo> getQuestionListByPage(PageDto pageDto);
}
