package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.guangyou.rareanimal.mapper.AnswerQuestionMapper;
import com.guangyou.rareanimal.mapper.QuestionMapper;
import com.guangyou.rareanimal.mapper.QuestionTagMapper;
import com.guangyou.rareanimal.pojo.AnswerQuestion;
import com.guangyou.rareanimal.pojo.Question;
import com.guangyou.rareanimal.pojo.QuestionTag;
import com.guangyou.rareanimal.pojo.dto.AnswerDto;
import com.guangyou.rareanimal.pojo.dto.ConfirmAnswerDto;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.dto.QuestionDto;
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
    public int publishQuestion(QuestionDto publishQuestionDto, Integer userId) {
        //1、根据userId 和 questionTitle 去数据库中查询，查询结果不为 0 则抛异常
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getUserId, userId);
        queryWrapper.eq(Question::getQuestionTitle, publishQuestionDto.getQuestionTitle());
        if (questionMapper.selectCount(queryWrapper) != 0){
            return -1;
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
        return questionId.intValue();
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
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Question::getUserId, userId);
        List<Question> questionList = questionMapper.selectList(queryWrapper);

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


//    @Override
//    public PageDataVo<QuestionVo> getQuestionListByPage(PageDto pageDto) {
//        PageDataVo<QuestionVo> pageDataVo = new PageDataVo<>();
//        //分页获取问题
//
//        pageDataVo.setPageData(copyUtils.opinionListCopy(opinionList));
//        pageDataVo.setCurrent(pageDto.getPage());
//        pageDataVo.setSize(pageDto.getPageSize());
//        int total = opinionMapper.selectCount(new LambdaQueryWrapper<Opinion>().eq(Opinion::getUserId,userId)).intValue();
//        pageDataVo.setTotal(total);
//        int isRemainZero = total%pageDto.getPageSize();
//        if (isRemainZero != 0){
//            pageDataVo.setPages( (total/pageDto.getPageSize()) + 1);
//        }else {
//            pageDataVo.setPages( total/pageDto.getPageSize() );
//        }
//
//        return pageDataVo;
//    }
}



