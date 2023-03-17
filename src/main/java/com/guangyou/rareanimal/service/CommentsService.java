package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.Comment;
import com.guangyou.rareanimal.pojo.dto.CommentDto;
import com.guangyou.rareanimal.pojo.vo.CommentsVo;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-03 17:00
 */
public interface CommentsService {

    /**
     * 根据文章id 查询相应的评论信息列表
     * @param articleId
     * @return
     */
    List<CommentsVo> findCommentsByArticleId(Long articleId);

    /**
     * 用户发表评论
     * @param commentDto
     * @return
     */
    CommentsVo publishComment(CommentDto commentDto, String userAccount);
}
