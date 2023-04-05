package com.guangyou.rareanimal.service.impl;

import com.guangyou.rareanimal.mapper.QuestionMapper;
import com.guangyou.rareanimal.mapper.QuestionTagMapper;
import com.guangyou.rareanimal.pojo.Question;
import com.guangyou.rareanimal.pojo.QuestionTag;
import com.guangyou.rareanimal.pojo.dto.QuestionDto;
import com.guangyou.rareanimal.pojo.dto.QuestionDto;
import com.guangyou.rareanimal.service.QuestionAnswersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        //1、先将 PublishQuestionDto 中的问题添加到 t_question 表中
        Question question = new Question();
        question.setUserId(userId);
        question.setQuestionTitle(publishQuestionDto.getQuestionTitle());
        question.setQuestionDescribe(publishQuestionDto.getQuestionDescribe());
        question.setIsUrgent(publishQuestionDto.getIsUrgent());
        question.setPublishTime(System.currentTimeMillis());
        questionMapper.insert(question);
//        String questionTitle = publishQuestionDto.getQuestionTitle();
//        String questionDescribe = publishQuestionDto.getQuestionDescribe();
//        Integer isUrgent = publishQuestionDto.getIsUrgent();
//        questionMapper.addQuestionByUser(userId,questionTitle,questionDescribe, (Long)System.currentTimeMillis(),isUrgent);
        //2、然后获取到 questionId
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
     * 2、根据 question_id 修改 t_answer_question 表中的数据
     * @param updateQuestionDto 修改问题需要的参数
     * @param userId 修改的用户的 id
     * @return 被修改的问题 id
     */
    @Override
    public int updateQuestion(QuestionDto updateQuestionDto, Integer userId) {
        //1、获取问题id，根据问题id 修改 t_question 表中的数据
        Long questionId = updateQuestionDto.getQuestionId();


        return 0;
    }
}



