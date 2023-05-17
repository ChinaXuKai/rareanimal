package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.AnswerQuestion;
import com.guangyou.rareanimal.pojo.dto.*;
import com.guangyou.rareanimal.pojo.vo.AnswerQuestionVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;
import com.guangyou.rareanimal.pojo.vo.QuestionVo;

import java.util.List;

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
    Long publishQuestion(QuestionDto publishQuestionDto, Integer userId);

    /**
     * 用户修改问题
     * @param updateQuestionDto 修改问题需要的参数
     * @param userId 修改的用户的 id
     * @return 被修改的问题 id
     */
    Long updateQuestion(QuestionDto updateQuestionDto, Integer userId);

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

    /**
     * 用户确认问题已被答复
     * @param confirmAnswerDto
     * @return
     */
    Long confirmAnswer(ConfirmAnswerDto confirmAnswerDto);

    /**
     * 问题按不同的排序条件分页
     * @param questionPageDto 问题分页的相关参数
     * @return 问题的分页数据集
     */
    PageDataVo<QuestionVo> getQuestionListByPage(Integer userId,QuestionPageDto questionPageDto);

    /**
     * 用户 点赞特定问题
     * @param userId 用户id
     * @return 问题id
     */
    Result supportQuestionByUid(Integer userId, Long questionId);

    /**
     * 用户 取消点赞特定问题
     * @param userId 用户id
     * @return 问题id
     */
    Result disSupportQuestionByUid(Integer userId, Long questionId);

    /**
     * 根据问题id 获得 对应回答列表
     * @param questionId 问题id
     * @return 问题回答列表
     */
    List<AnswerQuestionVo> getAnswersByQid(Long questionId);

//    /**
//     * 用户分页查看问题
//     * @param pageDto
//     * @return
//     */
//    PageDataVo<QuestionVo> getQuestionListByPage(PageDto pageDto);
}
