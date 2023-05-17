package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.Article;
import com.guangyou.rareanimal.pojo.dto.ArticleDto;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.dto.UserSupportDto;
import com.guangyou.rareanimal.pojo.vo.ArticleVo;
import com.guangyou.rareanimal.service.ArticleService;
import com.guangyou.rareanimal.utils.ArticleUtil;
import com.guangyou.rareanimal.utils.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author xukai
 * @create 2023-01-02 22:39
 */
@Slf4j
@RestController
@RequestMapping("/useArticle")
@Api(tags = "文章相关接口-需登录可看（都需要传jwt）")
public class UserArticleController {


    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "发布文章",notes = "用户通过此接口发布文章（需要传jwt）")
    @PostMapping("/publish")
    public Result publishArticle(@RequestBody @Validated ArticleDto articleDto){
        String userAccount = ShiroUtil.getProfile().getUserAccount();
        if (userAccount == null){
            throw new UnknownAccountException("你当前还没登录，登录后才能发表文章哦~");
        }

        Map map = articleService.addArticleToUser(articleDto,userAccount);
        if (!map.isEmpty()){
            return Result.succ(200, "文章发布成功", map);
        }else {
            return Result.fail("文章发布失败");
        }
    }


    @ApiOperation(value = "修改文章",notes = "用户通过此接口修改自己已发布文章（需要传jwt）")
    @PutMapping("/update")
    public Result updateArticle(@Validated ArticleDto articleDto){
        String userAccount = ShiroUtil.getProfile().getUserAccount();
        if (userAccount == null){
            throw new UnknownAccountException("你当前还没登录，登录后才能修改文章哦~");
        }

        Map map = articleService.updateArticleToUser(articleDto);
        if (!map.isEmpty()){
            return Result.succ(200, "文章修改成功", map);
        }else {
            return Result.fail("文章修改失败");
        }
    }


    @ApiOperation(value = "删除文章",notes = "用户通过输入密码进行删除自己已发布文章（需要传jwt）")
    @DeleteMapping("/delete")
    public Result deleteArticle(Long articleId,String password){
        String userAccount = ShiroUtil.getProfile().getUserAccount();
        if (userAccount == null){
            throw new UnknownAccountException("你当前还没登录，登录后才能删除文章哦~");
        }

        //先判断用户输入密码是否有误，错误不让删除，正确允许删除
        String userPwd = ShiroUtil.getProfile().getUserPwd();
        if (!userPwd.equals(password)){
            return Result.fail(Result.FORBIDDEN,"密码错误，请校验密码",null);
        }

        int deleteResult = articleService.deleteArticleToUser(articleId);
        if (deleteResult == 0){
            return Result.fail("删除失败");
        }
        return Result.succ(200, "删除成功", articleId);
    }


    @Autowired
    private ArticleUtil articleUtil;

    @ApiOperation(value = "收藏文章",notes = "用户通过此接口收藏文章（需要传jwt）")
    @PostMapping("/save/{articleId}")
    public Result save(@PathVariable("articleId") Long articleId){
        Integer userId = ShiroUtil.getProfile().getUserId();

        return articleService.saveArticleToUser(articleId,userId);
    }


    @ApiOperation(value = "取消收藏文章",notes = "用户通过此接口取消收藏文章（需要传jwt）")
    @DeleteMapping("/disSave/{articleId}")
    public Result disSave(@PathVariable("articleId") Long articleId){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("你当前还没登录，登录后才能取消收藏文章哦~");
        }

        int disSaveResult = articleService.disSaveArticleToUser(articleId,userId);
        if (disSaveResult == 0){
            return Result.fail("取消收藏失败");
        }else if (disSaveResult == -1){
            return Result.fail(Result.FORBIDDEN,"该文章还未收藏，不能进行该操作",null);
        }else {
            return Result.succ(200, "取消收藏成功", userId);
        }
    }


    @ApiOperation(value = "点赞文章或评论",notes = "用户通过此接口点赞文章或评论（需要传jwt）")
    @PostMapping("/support")
    public Result support(UserSupportDto supportDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("你当前还没登录，登录后才能点赞哦~");
        }

        Long commentId = supportDto.getCommentId();
        Long articleId = supportDto.getArticleId();
        //articleId、commentId参数不能同时为null，也不能同时不为null
        if ((articleId == null && commentId == null) || (articleId != null && commentId != null)){
            return Result.fail(Result.FORBIDDEN,"参数错误",null);
        }

        int supportResult;
        if (articleId == null){     //说明点赞的是评论
            supportResult = articleService.supportArticleOrComment(userId, null, commentId);
            if (supportResult == -1){
                return Result.fail("点赞失败");
            }
            return Result.succ(200, "点赞成功，感谢你的助力", commentId);
        }else {                     //说明点赞的是文章
            //先判断用户是否有权限访问该文章，没有则不允许收藏
            if (!articleUtil.haveArticleVisitPermission(userId.longValue(), articleId)){
                return Result.fail(Result.FORBIDDEN,"该博主设置了访问权限，你当前还没权限点赞哦",null);
            }
            //查询该文章的审核状态是否为 "审核通过"，必须为审核通过才能点赞
            if (!articleUtil.judgeIsPassAudit(articleId)) {  //若审核不通过
                return Result.fail(Result.FORBIDDEN,"当前文章未通过审核，不能点赞该文章",null );
            }

            //根据userId、articleId查询 t_user_support表中 是否已经有数据，若有，则不允许点赞
            supportResult = articleService.supportArticleOrComment(userId,articleId,null);
            if (supportResult == -1){
                return Result.fail("点赞失败");
            }
            return Result.succ(200, "点赞成功，感谢你的助力", articleId);
        }

    }


    @ApiOperation(value = "取消点赞文章或评论",notes = "用户通过此接口取消点赞文章或评论（需要传jwt）")
    @DeleteMapping("/disSupport")
    public Result disSupport(UserSupportDto disSupportDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("你当前还没登录，登录后才能取消点赞哦~");
        }

        Long articleId = disSupportDto.getArticleId();
        Long commentId = disSupportDto.getCommentId();
        if ((articleId == null && commentId == null) || (articleId != null && commentId != null)){
            return Result.fail(Result.FORBIDDEN,"参数错误",null);
        }

        int disSupportResult;
        if (articleId == null){     //说明取消点赞的是评论
            disSupportResult = articleService.disSupportArticleOrComment(userId,null,commentId);
            if (disSupportResult == -1){
                return Result.fail("取消点赞失败");
            }
            return Result.succ(200, "取消点赞成功", commentId);
        }else {                     //说明取消点赞的是文章
            disSupportResult = articleService.disSupportArticleOrComment(userId,articleId,null);
            if (disSupportResult == -1){
                return Result.fail("取消点赞失败");
            }
            return Result.succ(200, "取消点赞成功", articleId);
        }
    }


    @ApiOperation(value = "关注博主",notes = "用户通过此接口关注博主（需要传jwt）")
    @PostMapping("/careAuthor/{authorId}")
    public Result careAuthor(@PathVariable("authorId") Integer authorId){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("你当前还没登录，登录后才能关注该博主哦~");
        }

        int careResult = articleService.careAuthorByArticleId(authorId,userId);
        if (careResult == -1){
            return Result.fail("关注失败");
        }else {
            return Result.succ(200, "关注成功，感谢你的支持", authorId);
        }
    }


    @ApiOperation(value = "取消关注博主",notes = "用户通过此接口取消关注博主（需要传jwt）")
    @DeleteMapping("/disCareAuthor/{authorId}")
    public Result disCareAuthor(@PathVariable("authorId") Integer authorId){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("你当前还没登录，登录后才能取消关注哦~");
        }

        int disCareResult = articleService.disCareAuthorByAuthorId(authorId,userId);
        if (disCareResult == -1){
            return Result.fail("取消关注失败");
        }else {
            return Result.succ(200, "取消关注成功", authorId);
        }
    }


}
