package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.mapper.AnswerQuestionMapper;
import com.guangyou.rareanimal.mapper.QuestionMapper;
import com.guangyou.rareanimal.mapper.QuestionTagMapper;
import com.guangyou.rareanimal.mapper.SupportQuestionMapper;
import com.guangyou.rareanimal.pojo.AnswerQuestion;
import com.guangyou.rareanimal.pojo.Question;
import com.guangyou.rareanimal.pojo.QuestionTag;
import com.guangyou.rareanimal.pojo.SupportQuestion;
import com.guangyou.rareanimal.pojo.dto.*;
import com.guangyou.rareanimal.pojo.vo.AnswerQuestionVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;
import com.guangyou.rareanimal.pojo.vo.QuestionVo;
import com.guangyou.rareanimal.service.QuestionAnswersService;
import com.guangyou.rareanimal.utils.CopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author xukai
 * @create 2023-03-31 21:29
 */
@Service
public class QuestionAnswersServiceImpl implements QuestionAnswersService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionTagMapper questionTagMapper;

    /**
     * 发表问题（开启事务）：
     * 1、查看数据库中 同一个用户是否有同名的问题，有则抛异常
     * 2、先将 PublishQuestionDto 中的问题添加到 t_question 表中
     * 3、获取question.getQuestionId()，创建 questionTag 对象，赋值进 t_question_tag
     * @param publishQuestionDto 发表问题需要的参数
     * @param userId 发表用户的id
     * @return
     */
    @Transactional
    @Override
    public Long publishQuestion(QuestionDto publishQuestionDto, Integer userId) {
        //1、根据userId 和 questionTitle 去数据库中查询，查询结果不为 0 则抛异常
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getUserId, userId);
        queryWrapper.eq(Question::getQuestionTitle, publishQuestionDto.getQuestionTitle());
        if (questionMapper.selectCount(queryWrapper) != 0){
            return null;
        }
        //2、先将 PublishQuestionDto 中的问题添加到 t_question 表中
        Question question = new Question();
        question.setUserId(userId);
        question.setQuestionTitle(publishQuestionDto.getQuestionTitle());
        question.setQuestionDescribe(publishQuestionDto.getQuestionDescribe());
        question.setIsUrgent(publishQuestionDto.getIsUrgent());
        question.setIsFinish(0);
        question.setPublishTime(System.currentTimeMillis());
        questionMapper.insert(question);
        //3、然后获取到 questionId，创建 questionTag 对象，赋值进 t_question_tag
        Long questionId = question.getQuestionId();
        List<String> questionTagsInfo = publishQuestionDto.getQuestionTags();
        for (String questionTagInfo : questionTagsInfo){
            QuestionTag questionTag = new QuestionTag();
            questionTag.setQuestionId(questionId);
            questionTag.setTagInfo(questionTagInfo);
            questionTagMapper.insert(questionTag);
        }
        return questionId;
    }


    /**
     * 修改问题（开启事务）：
     * 1、根据 question_id 修改 t_question 表中的数据
     * 2、根据 question_id 修改 t_question_tag 表中的数据
     * @param updateQuestionDto 修改问题需要的参数
     * @param userId 修改的用户的 id
     * @return 被修改的问题 id
     */
    @Transactional
    @Override
    public Long updateQuestion(QuestionDto updateQuestionDto, Integer userId) {
        Long questionId = updateQuestionDto.getQuestionId();
        //1、根据问题id 修改 t_question 表中的数据
        Question question = new Question();
        question.setUpdateTime(System.currentTimeMillis());
        question.setIsUrgent(updateQuestionDto.getIsUrgent());
        question.setQuestionTitle(updateQuestionDto.getQuestionTitle());
        question.setQuestionDescribe(updateQuestionDto.getQuestionDescribe());
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getQuestionId,questionId);
        questionMapper.update(question,queryWrapper);
        //2、根据 question_id 修改 t_question_tag 表中的数据
        List<String> questionTagList = updateQuestionDto.getQuestionTags();
        for (String tag : questionTagList){
            QuestionTag questionTag = new QuestionTag();
            questionTag.setTagInfo(tag);
            LambdaQueryWrapper<QuestionTag> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(QuestionTag::getQuestionId,questionId);
            questionTagMapper.update(questionTag, wrapper);
        }
        return updateQuestionDto.getQuestionId();
    }


    @Autowired
    private AnswerQuestionMapper answerQuestionMapper;

    @Override
    public int replyQuestion(AnswerDto answerDto, Integer userId) {
        //若该问题id 不存在，则不进行添加
        Question dbQuestion = questionMapper.selectOne(
                new LambdaQueryWrapper<Question>()
                        .eq(Question::getQuestionId, answerDto.getQuestionId()));
        if (dbQuestion == null){
            return -1;
        }

        //添加回答到 t_answer_question 表 中
        AnswerQuestion answerQuestion = new AnswerQuestion();
        answerQuestion.setAnswerContent(answerDto.getAnswerContent());
        answerQuestion.setUserId(userId.longValue());
        answerQuestion.setQuestionId(answerDto.getQuestionId());
        answerQuestion.setIsPerfectAnswer(0);
        answerQuestionMapper.insert(answerQuestion);
        return answerQuestion.getAnswerId().intValue();
    }


    @Autowired
    private CopyUtils copyUtils;

    @Override
    public PageDataVo<QuestionVo> getMyQuestionsByPage(PageDto pageDto, Integer userId) {
        PageDataVo<QuestionVo> pageDataVo = new PageDataVo<>();
        //分页获取问题
        List<Question> questionList = questionMapper.getQuestionByPageAndUid(userId,pageDto.getPageSize() * (pageDto.getPage() - 1), pageDto.getPageSize());

        pageDataVo.setPageData(copyUtils.questionListCopy(userId,questionList));
        pageDataVo.setCurrent(pageDto.getPage());
        pageDataVo.setSize(pageDto.getPageSize());
        int total = questionMapper.selectCount(new LambdaQueryWrapper<Question>().eq(Question::getUserId,userId)).intValue();
        pageDataVo.setTotal(total);
        int isRemainZero = total%pageDto.getPageSize();
        if (isRemainZero != 0){
            pageDataVo.setPages( (total/pageDto.getPageSize()) + 1);
        }else {
            pageDataVo.setPages( total/pageDto.getPageSize() );
        }

        return pageDataVo;
    }


    /**
     * 用户确认问题已被答复
     * 1、将 t_question 表的 is_finish 修改为 1
     * 2、将 t_answer_question 表中对应的 回答 的 is_perfect_answer 修改为 1
     * @param confirmAnswerDto
     * @return
     */
    @Transactional
    @Override
    public Long confirmAnswer(ConfirmAnswerDto confirmAnswerDto) {
        Long questionId = confirmAnswerDto.getQuestionId();
        //1、将 t_question 表的 is_finish 修改为 1
        LambdaUpdateWrapper<Question> questionUpdateWrapper = new LambdaUpdateWrapper<>();
        questionUpdateWrapper.eq(Question::getQuestionId,questionId);
        Question question = new Question();
        question.setIsFinish(1);
        questionMapper.update(question, questionUpdateWrapper);
        //2、将 t_answer_question 表中对应的 回答 的 is_perfect_answer 修改为 1
        LambdaUpdateWrapper<AnswerQuestion> answerUpdateWrapper = new LambdaUpdateWrapper<>();
        answerUpdateWrapper.eq(AnswerQuestion::getQuestionId, questionId);
        answerUpdateWrapper.eq(AnswerQuestion::getAnswerId, confirmAnswerDto.getAnswerId());
        AnswerQuestion answerQuestion = new AnswerQuestion();
        answerQuestion.setIsPerfectAnswer(1);
        answerQuestionMapper.update(answerQuestion, answerUpdateWrapper);
        return questionId;
    }


    /**
     * 分排序情况 分页问题
     * @param questionPageDto 问题分页的相关参数
     * @return
     */
    @Override
    public PageDataVo<QuestionVo> getQuestionListByPage(Integer userId,QuestionPageDto questionPageDto) {
        PageDataVo<QuestionVo> pageDataVo = new PageDataVo<>();
        List<Question> questionList = null;
        List<QuestionVo> questionVoList = null;
        PageDto pageDto = questionPageDto.getPageDto();
        int total = 0;

        //按时间排序：从最近到以前，即从大到小
        if (questionPageDto.getIsNew() == 1){
            questionList = questionMapper.getQuestionOrderTimeByPage(pageDto.getPageSize()*(pageDto.getPage() - 1), pageDto.getPageSize());
            total = questionMapper.selectCount(null).intValue();
        }
        //按热度排序：回答数从大到小
        if (questionPageDto.getIsTop() == 1){
            questionList = questionMapper.getQuestionOrderAnswersByPage(pageDto.getPageSize()*(pageDto.getPage() - 1), pageDto.getPageSize());
            total = questionMapper.selectCount(null).intValue();
        }
        //按已完成筛选：筛选出已完成的，然后按时间从大到小
        if (questionPageDto.getIsFinish() == 1){
            questionList = questionMapper.getFinishQuestionByPage(pageDto.getPageSize()*(pageDto.getPage() - 1), pageDto.getPageSize());
            LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Question::getIsFinish, 1);
            total = questionMapper.selectCount(queryWrapper).intValue();
        }
        //按紧急筛选：筛选出已完成的，然后按时间从大到小
        if (questionPageDto.getIsUrgent() == 1){
            questionList = questionMapper.getUrgentQuestionByPage(pageDto.getPageSize()*(pageDto.getPage() - 1), pageDto.getPageSize());
            LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Question::getIsUrgent, 1);
            total = questionMapper.selectCount(queryWrapper).intValue();
        }

        questionVoList = copyUtils.questionListCopy(userId, questionList);
        pageDataVo.setPageData(questionVoList);
        pageDataVo.setCurrent(pageDto.getPage());
        pageDataVo.setSize(pageDto.getPageSize());
        pageDataVo.setTotal(total);
        int isRemainZero = total%pageDto.getPageSize();
        if (isRemainZero != 0){
            pageDataVo.setPages( (total/pageDto.getPageSize()) + 1);
        }else {
            pageDataVo.setPages( total/pageDto.getPageSize() );
        }
        return pageDataVo;
    }


    @Autowired
    private SupportQuestionMapper supportQuestionMapper;

    /**
     * 用户点赞 特定问题
     * @param userId 用户id
     * @param questionId 问题id
     * @return 结果集
     */
    @Override
    public Result supportQuestionByUid(Integer userId, Long questionId) {
        if (userId == null){
            return Result.fail(Result.FORBIDDEN, "当前为登录，请登录后再进行该操作", null);
        }

        //在 t_support_question 表中查询是否已有数据
        LambdaQueryWrapper<SupportQuestion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SupportQuestion::getQuestionId,questionId);
        queryWrapper.eq(SupportQuestion::getUserId, userId);
        Long isExist = supportQuestionMapper.selectCount(queryWrapper);

        //表中 已有数据：该用户已经点赞过该问题，不可以点赞该问题
        if (isExist != 0){
            return Result.fail(Result.FORBIDDEN, "你已点赞过该问题，不能重复操作", questionId);
        }
        //表中 没有数据：该用户可以点赞该问题
        SupportQuestion supportQuestion = new SupportQuestion();
        supportQuestion.setUserId(userId);
        supportQuestion.setQuestionId(questionId);
        supportQuestionMapper.insert(supportQuestion);

        Long supportId = supportQuestion.getSupportId();
        if (supportId == null || supportId == 0){
            return Result.fail("点赞" + questionId + "失败");
        }
        return Result.succ(200, "点赞" + questionId + "成功", questionId);
    }


    /**
     * 用户取消点赞 特定问题
     * @param userId 用户id
     * @param questionId 问题id
     * @return 结果集
     */
    @Override
    public Result disSupportQuestionByUid(Integer userId, Long questionId) {
        if (userId == null){
            return Result.fail(Result.FORBIDDEN, "当前为登录，请登录后再进行该操作", null);
        }

        //先在 t_support_question 表中查询 是否有数据
        LambdaQueryWrapper<SupportQuestion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SupportQuestion::getQuestionId,questionId);
        queryWrapper.eq(SupportQuestion::getUserId, userId);
        Long isExist = supportQuestionMapper.selectCount(queryWrapper);

        //表中 有数据：根据 问题id、用户id 删除该数据
        if (isExist != 0){
            supportQuestionMapper.delete(
                    new LambdaQueryWrapper<SupportQuestion>()
                            .eq(SupportQuestion::getQuestionId, questionId)
                            .eq(SupportQuestion::getUserId, userId));
            return Result.succ(200, "取消点赞" + questionId + "成功", null);
        }
        //表中 没数据：返回错误
        return Result.fail(Result.FORBIDDEN, "你当前还未对该问题进行点赞操作，无法进行取消点赞操作", null);
    }


    @Override
    public List<AnswerQuestionVo> getAnswersByQid(Long questionId) {
        //answers：根据 问题id 在 t_answer_question表 中查找集合
        List<AnswerQuestion> answerList = answerQuestionMapper.selectList(
                new LambdaQueryWrapper<AnswerQuestion>().
                        eq(AnswerQuestion::getQuestionId, questionId));
        return copyUtils.answerQuestionListCopy(answerList);
    }


}



