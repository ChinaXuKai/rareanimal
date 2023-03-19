package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.ArticleVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;
import com.guangyou.rareanimal.pojo.vo.UserCarerVo;
import com.guangyou.rareanimal.service.ArticleService;
import com.guangyou.rareanimal.service.PersonalCenterService;
import com.guangyou.rareanimal.utils.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

        PageDataVo<ArticleVo> pageDataVo = personalCenterService.getMyArticles(pageDto,userAccount);

        if (pageDataVo.getPageData().isEmpty()){
            return Result.succ(200,"当前还未发表过文章，快来发表文章让大家知道你吧~",null);
        }else {
            return Result.succ(200, "下列为你已发表的文章",pageDataVo);
        }
    }


    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "我的收藏文章列表",notes = "用户通过此接口查看自己的收藏文章列表（需要传jwt）")
    @GetMapping("/getMySaveArticles")
    public Result getMySaveArticles(PageDto pageDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("当前还未登录，请先登录再访问该页面");
        }

        PageDataVo<ArticleVo> pageDataVo = articleService.listSaveArticles(pageDto,userId);

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
}
