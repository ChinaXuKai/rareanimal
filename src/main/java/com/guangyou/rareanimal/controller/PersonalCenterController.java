package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.mapper.QuestionMapper;
import com.guangyou.rareanimal.pojo.Question;
import com.guangyou.rareanimal.pojo.dto.ConfirmAnswerDto;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.dto.QuestionDto;
import com.guangyou.rareanimal.pojo.vo.*;
import com.guangyou.rareanimal.service.ArticleService;
import com.guangyou.rareanimal.service.OpinionService;
import com.guangyou.rareanimal.service.PersonalCenterService;
import com.guangyou.rareanimal.service.QuestionAnswersService;
import com.guangyou.rareanimal.utils.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-21 22:28
 */
@Slf4j
@RestController
@Api(tags = "个人中心控制器（都需要传jwt）")
@RequestMapping("/personalCenter")
public class PersonalCenterController {

    @Autowired
    private PersonalCenterService personalCenterService;

    @ApiOperation(value = "我的粉丝数",notes = "获取当前用户的粉丝数")
    @GetMapping("/getMyFans")
    public Result getMyFans(){
        //获取当前的用户id为作者id
        Integer authorId = ShiroUtil.getProfile().getUserId();
        if (authorId == null){
            throw new UnknownAccountException("当前还未登录，请先登录再访问该页面");
        }

        //根据作者id查询粉丝数
        int fansCounts = personalCenterService.getMyFansCounts(authorId);
        if (fansCounts > 0){
            return Result.succ(200,"你已有" + fansCounts +"个粉丝了，继续加油",fansCounts);
        }else {
            return Result.succ(200, "你当前还没有粉丝哦，快让大家知道你吧", null);
        }
    }


    @ApiOperation(value = "我的文章",notes = "获取当前用户的所发表过的所有文章")
    @GetMapping("/getMyArticles")
    public Result getMyArticles(PageDto pageDto){
        String userAccount = ShiroUtil.getProfile().getUserAccount();
        if (userAccount == null){
            throw new UnknownAccountException("当前还未登录，请先登录再访问该页面");
        }

        Integer userId = ShiroUtil.getProfile().getUserId();
        PageDataVo<ArticleVo> pageDataVo = personalCenterService.getMyArticles(pageDto,userAccount,userId);

        if (pageDataVo.getPageData().isEmpty()){
            return Result.succ(200,"当前还未发表过文章，快来发表文章让大家知道你吧~",null);
        }else {
            return Result.succ(200, "下列为你已发表的文章",pageDataVo);
        }
    }


    @ApiOperation(value = "我的收藏文章列表",notes = "用户通过此接口查看自己的收藏文章列表（需要传jwt）")
    @GetMapping("/getMySaveArticles")
    public Result getMySaveArticles(PageDto pageDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("当前还未登录，请先登录再访问该页面");
        }

        PageDataVo<ArticleVo> pageDataVo = personalCenterService.listSaveArticles(pageDto,userId);

        if (pageDataVo.getPageData().isEmpty()){
            return Result.succ(200, "当前还没有收藏文章哦~", null);
        }else {
            return Result.succ(200, "以下为你所收藏的文章", pageDataVo);
        }
    }


    @ApiOperation(value = "我的关注",notes = "获取当前用户所关注的所有博主")
    @GetMapping("/getMyCarers")
    public Result getMyCarers(PageDto pageDto){
        //获取当前用户id
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("当前还未登录，请先登录再访问该页面");
        }

        //根据userId 查询用户关注的博主用户
        UserCarerVo myCarers = personalCenterService.getMyCarers(pageDto,userId);
        if (myCarers.getPageBloggerData().getPageData().isEmpty()){
            return Result.succ(200, "你还没有关注的博主哦，快去看看别人的空间吧", null);
        }else {
            return Result.succ(200, "下列为你所关注的博主", myCarers);
        }
    }


    @Autowired
    private OpinionService opinionService;

    @ApiOperation(value = "我的意见")
    @GetMapping("/getOpinionsByPage")
    public Result getOpinionsByPage(PageDto pageDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            return Result.fail(Result.FORBIDDEN,"当前未登录，还不能发表意见哦",null);
        }

        PageDataVo<OpinionVo> opinionVoPage = opinionService.getOpinionsByPage(pageDto,userId);
        if (opinionVoPage.getPageData().isEmpty()){
            return Result.succ("你当前还没提交过意见哦");
        }
        return Result.succ(200, "你发表过的意见如下", opinionVoPage);
    }


    @Autowired
    private QuestionAnswersService questionAnswersService;

    @ApiOperation(value = "我的问题")
    @GetMapping("/getMyQuestionsByPage")
    public Result getMyQuestionsByPage(PageDto pageDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            return Result.fail(Result.FORBIDDEN,"当前未登录，还不能发表意见哦",null);
        }

        PageDataVo<QuestionVo> questionVoPage = questionAnswersService.getMyQuestionsByPage(pageDto,userId);
        if (questionVoPage.getPageData().isEmpty()){
            return Result.succ("你当前还没提出过问题哦");
        }
        return Result.succ(200, "你提出过的问题如下", questionVoPage);
    }


    @ApiOperation(value = "用户确认问题已被答复")
    @PutMapping("/confirmAnswer")
    public Result confirmAnswer(@RequestBody ConfirmAnswerDto confirmAnswerDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            return Result.fail(Result.FORBIDDEN,"当前未登录，还不能发表意见哦",null);
        }

        Long confirmResult = questionAnswersService.confirmAnswer(confirmAnswerDto);
        if (confirmResult == 0 || confirmResult == null){
            return Result.fail("确认答复出现异常");
        }
        return Result.succ(200, "问题id："+confirmAnswerDto.getQuestionId()+"已确认答复", confirmResult);
    }


    @ApiOperation(value = "用户修改问题",notes = "用户登录后可修改自己提出的问答（需要传jwt）")
    @PutMapping("/updateQuestion")
    public Result updateQuestion(@RequestBody QuestionDto updateQuestionDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null) {
            throw new UnknownAccountException("当前还未登录，还不能修改问题哦~");
        }

        Long questionId = questionAnswersService.updateQuestion(updateQuestionDto,userId);
        if (questionId == 0 || questionId == null){
            return Result.fail("问题修改出现异常");
        }
        return Result.succ(200, "问题修改成功", questionId);
    }

}
