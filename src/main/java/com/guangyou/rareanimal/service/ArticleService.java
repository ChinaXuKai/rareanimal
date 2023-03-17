package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.ArticleDto;
import com.guangyou.rareanimal.pojo.vo.ArticleVo;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;

import java.util.List;
import java.util.Map;

/**
 * @author xukai
 * @create 2022-12-28 10:04
 */
public interface ArticleService {

    /**
     * 分页查询文章列表
     * @param pageDto
     * @return
     */
    Result listArticle(PageDto pageDto);

    /**
     * 查询最热文章
     * @param hotArticleLimit
     * @return
     */
    List<ArticleVo> getHotArticle(int hotArticleLimit);

    /**
     * 查询最新文章
     * @param newArticleLimit
     * @return
     */
    List<ArticleVo> getNewArticle(int newArticleLimit);

    /**
     * 根据 articleId 查看相应的文章信息
     * @param articleId
     * @return
     */
    ArticleVo findArticleById(Long articleId);

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
    String deleteArticleToUser(Long articleId);

    /**
     * 用户userId 收藏文章articleId
     * @param articleId
     * @param userId
     * @return
     */
    int saveArticleToUser(Long articleId, Integer userId);

    /**
     * 用户userId 取消收藏文章articleId
     * @param articleId
     * @param userId
     * @return
     */
    int disSaveArticleToUser(Long articleId, Integer userId);

    /**
     * 用户userId 查看收藏文章列表
     * @param pageDto
     * @param userId
     * @return
     */
    PageDataVo<ArticleVo> listSaveArticles(PageDto pageDto, Integer userId);

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
}
