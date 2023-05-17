package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.AnswerQuestion;
import com.guangyou.rareanimal.pojo.Question;
import com.guangyou.rareanimal.pojo.dto.AnswerDto;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.dto.QuestionDto;
import com.guangyou.rareanimal.pojo.dto.QuestionPageDto;
import com.guangyou.rareanimal.pojo.vo.AnswerQuestionVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;
import com.guangyou.rareanimal.pojo.vo.QuestionVo;
import com.guangyou.rareanimal.service.QuestionAnswersService;
import com.guangyou.rareanimal.utils.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation(value = "用户提出问题",notes = "用户登录后可提出问答（需要传jwt）")
    @PostMapping("/publishQuestion")
    public Result publishQuestion(@RequestBody QuestionDto publishQuestionDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null) {
            throw new UnknownAccountException("当前还未登录，还不能发表问题哦~");
        }

        Long questionId = questionAnswersService.publishQuestion(publishQuestionDto,userId);
        if (questionId == 0 || questionId == null){
            return Result.fail("问题发表出现异常");
        }else if (questionId == -1){
            return Result.fail(Result.FORBIDDEN,"你发表过同标题的问题，请更换标题",null);
        }
        return Result.succ(200, "问题发表成功，问题id为："+questionId, questionId);
    }


    @ApiOperation(value = "用户回答问题",notes = "用户登录后可回答别人发表的问题")
    @PostMapping("/answerQuestion")
    public Result answerQuestion(@RequestBody AnswerDto answerDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null) {
            throw new UnknownAccountException("当前还未登录，还不能修改问题哦~");
        }

        int answerId = questionAnswersService.replyQuestion(answerDto,userId);
        if (answerId == 0){
            return Result.fail("用户回答问题出现异常");
        } else if (answerId == -1) {
            return Result.fail(Result.FORBIDDEN,"问题id不存在，请重新确认参数",null);
        }
        return Result.succ(200, "用户回答问题成功，回答id为："+answerId, answerId);
    }


    @ApiOperation(value = "根据问题id查看问题回答")
    @GetMapping("/getAnswersByQid")
    public Result getAnswersByQid(Long questionId){
        List<AnswerQuestionVo> answerList = questionAnswersService.getAnswersByQid(questionId);
        if (answerList.isEmpty()){
            return Result.succ(200,"该问题当前还没有回答",null);
        }

        return Result.succ(200, "该问题有以下回答", answerList);
    }


    @ApiOperation(value = "用户根据排序条件分页查看相关问题",notes = "用户分页查看问题")
    @GetMapping("/getQuestionListByPage")
    public Result getQuestionListByPage(QuestionPageDto questionPageDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        PageDataVo<QuestionVo> questionVoPage = questionAnswersService.getQuestionListByPage(userId,questionPageDto);

        if (questionVoPage.getPageData().isEmpty()){
            return Result.succ("当前还没有人发表问题哦~");
        }
        return Result.succ(200,"问题列表如下",questionVoPage);
    }


    @ApiOperation(value = "用户点赞问题",notes = "用户点赞问题")
    @PostMapping("/supportQuestionByUid")
    public Result supportQuestionByUid(Long questionId){
        Integer userId = ShiroUtil.getProfile().getUserId();
        return questionAnswersService.supportQuestionByUid(userId,questionId);
    }
    
    
    @ApiOperation(value = "用户取消点赞问题",notes = "用户取消点赞问题")
    @DeleteMapping("/disSupportQuestionByUid")
    public Result disSupportQuestionByUid(Long questionId){
        Integer userId = ShiroUtil.getProfile().getUserId();
        return questionAnswersService.disSupportQuestionByUid(userId, questionId);
    }
}
