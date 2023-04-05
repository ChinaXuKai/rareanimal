package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.QuestionDto;
import com.guangyou.rareanimal.service.QuestionAnswersService;
import com.guangyou.rareanimal.utils.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xukai
 * @create 2023-03-30 19:30
 */
@Slf4j
@RestController
@RequestMapping("/questionAnswers")
@Api(tags = "问答系统")
public class QuestionAnswersController {

    @Autowired
    private QuestionAnswersService questionAnswersService;

    @ApiOperation(value = "用户提出问答",notes = "用户登录后可提出问答（需要传jwt）")
    @PostMapping("/publishQuestion")
    public Result publishQuestion(@RequestBody QuestionDto publishQuestionDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null) {
            throw new UnknownAccountException("当前还未登录，还不能发表问题哦~");
        }

        int questionId = questionAnswersService.publishQuestion(publishQuestionDto,userId);
        if (questionId == 0){
            return Result.fail("问题发表出现异常");
        }
        return Result.succ(200, "问题发表成功", questionId);
    }


    @ApiOperation(value = "用户修改问答",notes = "用户登录后可修改自己提出的问答（需要传jwt）")
    @PostMapping("/updateQuestion")
    public Result updateQuestion(@RequestBody QuestionDto updateQuestionDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null) {
            throw new UnknownAccountException("当前还未登录，还不能修改问题哦~");
        }

        int questionId = questionAnswersService.updateQuestion(updateQuestionDto,userId);
        if (questionId == 0){
            return Result.fail("问题修改出现异常");
        }
        return Result.succ(200, "问题修改成功", questionId);
    }
}
