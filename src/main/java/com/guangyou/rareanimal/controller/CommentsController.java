package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.Comment;
import com.guangyou.rareanimal.pojo.dto.CommentDto;
import com.guangyou.rareanimal.pojo.vo.CommentsVo;
import com.guangyou.rareanimal.service.ArticleService;
import com.guangyou.rareanimal.service.CommentsService;
import com.guangyou.rareanimal.utils.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-03 14:45
 */
@Slf4j
@RestController
@RequestMapping("/comments")
@Api(tags = "评论相关接口")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @ApiOperation(value = "评论列表",notes = "获取特定文章的评论列表（不需要传jwt）")
    @GetMapping("/getCommentList/{id}")
    public Result getCommentList(@PathVariable("id") Long articleId){
        List<CommentsVo> commentsVos = commentsService.findCommentsByArticleId(articleId);
        if (commentsVos.size() == 0){
            return Result.succ("该文章目前还没有评论哦~");
        }else {
            return Result.succ(200, "欢迎用户文明评论", commentsVos);
        }
    }


    @Autowired
    private ArticleService articleService;

    @ApiOperation(value = "发表评论",notes = "在特定文章发表评论（需要传jwt）")
    @PostMapping("/publishComment")
    public Result publishComment( CommentDto commentDto){
        //1、获取当前用户账号
        String userAccount = ShiroUtil.getProfile().getUserAccount();
            //用户未登录不允许评论
        if (userAccount == null){
            return Result.fail("请登录后再发表评论哦~");
        }

        //2、发表评论
        CommentsVo comment = commentsService.publishComment(commentDto, userAccount);
        if (comment.getId() != null){
            //3、发表评论成功后，若level为1，则要修改t_article表中的comment_counts字段值 + 1
            if (comment.getLevel() == 1){
                articleService.increaseCommentCountsByArticleId(commentDto.getArticleId());
            }

            return Result.succ(200, "发表评论成功", comment);
        }else {
            return Result.fail("发表评论失败");
        }
    }

}
