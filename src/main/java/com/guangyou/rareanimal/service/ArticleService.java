package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.ArticleDto;
import com.guangyou.rareanimal.pojo.vo.ArticleVo;

import java.util.List;
import java.util.Map;

/**
 * @author xukai
 * @create 2022-12-28 10:04
 */
public interface ArticleService {

    /**
     * 查询官方发布的文章列表
     * @return
     */
    Result getOfficialArticles(Integer userId);

    /**
     * 查询用户发布的文章列表
     * @return
     */
    Result getUserArticles(Integer userId);

    /**
     * 查询非官方发布的最热文章
     * @return
     */
    List<ArticleVo> getHotArticle(Integer userId);

    /**
     * 查询非官方发布的最新文章
     * @return
     * @param userId
     */
    List<ArticleVo> getNewArticle(Integer userId);

    /**
     * 根据 articleId 查看相应的文章信息
     * @param articleId
     * @return
     */
    ArticleVo findArticleById(Integer userId,Long articleId);

    /**
     * 修改t_article表中的comment_counts字段值 + 1
     * @param articleId
     */
    void increaseCommentCountsByArticleId(Long articleId);

    /**
     * 用户登录账号userAccount后，发表文章
     * @param articleDto
     * @param userAccount
     * @return
     */
    Map addArticleToUser(ArticleDto articleDto, String userAccount);

    /**
     * 用户登录账号userAccount后，修改文章
     * @param articleDto
     * @return
     */
    Map updateArticleToUser(ArticleDto articleDto);

    /**
     * 用户根据 articleId删除文章
     * @param articleId
     * @return
     */
    int deleteArticleToUser(Long articleId);

    /**
     * 用户userId 收藏文章articleId
     * @param articleId
     * @param userId
     * @return
     */
    Result saveArticleToUser(Long articleId, Integer userId);

    /**
     * 用户userId 取消收藏文章articleId
     * @param articleId
     * @param userId
     * @return
     */
    int disSaveArticleToUser(Long articleId, Integer userId);


    /**
     * 用户userId 点赞文章或评论
     * @param userId
     * @param articleId
     * @param commentId
     */
    int supportArticleOrComment(Integer userId, Long articleId, Long commentId);

    /**
     * 用户userId 取消点赞文章或评论
     * @param userId
     * @param articleId
     * @param commentId
     */
    int disSupportArticleOrComment(Integer userId, Long articleId, Long commentId);

    /**
     * 用户userId 通过authorId 关注博主
     * @param authorId 被关注的博主
     * @param userId 用户
     * @return
     */
    int careAuthorByArticleId(Integer authorId, Integer userId);

    /**
     * 用户userId 通过authorId 取消关注博主
     * @param authorId
     * @param userId
     * @return
     */
    int disCareAuthorByAuthorId(Integer authorId, Integer userId);

    /**
     * 根据圈子id 获取对应的 文章集合
     * @param categoryId 圈子id
     * @return 文章集合
     */
    Result getArticlesByCategoryId(Integer userId,Integer categoryId);
}
