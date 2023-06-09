package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.Article;
import com.guangyou.rareanimal.pojo.vo.ArticleVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2022-12-28 16:49
 */
@Repository
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 用户阅读文章后增加阅读数量
     * @param articleId
     */
    void increaseCommentCountsByArticleId(Long articleId);

    /**
     * 用户查看收藏文章列表
     * @param userId
     * @param initialDataLocation 上一页最后一个数据的位置/当前页初始数据的前一个位置
     * @param pageSize 每页要显示数据的数量
     * @return
     */
    List<Article> selectSaveArticles(Integer userId, Integer initialDataLocation, Integer pageSize);

    /**
     * 通过文章id 获取对应评论的 support_counts字段值
     * @param articleId
     * @return
     */
    Integer selectSupportCountsByArticleId(Long articleId);

    /**
     * 通过文章id 增加对应文章的 support_counts字段值
     * @param articleId
     * @param beforeSupportCounts
     */
    int increaseSupportCounts(Long articleId, int beforeSupportCounts);

    /**
     * 通过文章id 减小对应文章的 support_counts字段值
     * @param articleId
     * @param beforeSupportCounts
     */
    int decreaseSupportCounts(Long articleId, int beforeSupportCounts);

    /**
     * 通过文章id 增加对应文章的 save_counts字段值
     * @param articleId
     * @param beforeSaveCounts
     * @return
     */
    int increaseSaveCounts(Long articleId, int beforeSaveCounts);

    /**
     * 通过文章id 减小对应文章的 save_counts字段值
     * @param articleId
     * @param beforeSaveCounts
     * @return
     */
    int decreaseSaveCounts(Long articleId, int beforeSaveCounts);

    /**
     * 根据用户账号查询 对应用户发表的文章篇数
     * @param userAccount 用户账号
     * @return
     */
    int getArticleCountByUser(String userAccount);

    /**
     * 根据文章id 查询访问权限
     * @param articleId 文章id
     * @return
     */
    String selectVisitPermission(Long articleId);

    /**
     * 获取 用户的 可读的 文章集合
     * 要求：用户文章、访问权限不能是仅我可见
     * @param auditState 被选取的审核状态
     * @param visitPermission 访问权限
     * @return
     */
    List<Article> selectUserArticles(String auditState,String visitPermission);

    /**
     * 获取官方发表的前 OFFICIAL_ARTICLE_NUMBER 条文章
     * @param auditState 被选取的审核状态
     * @param officialArticleNumber 限制展示的文章数
     * @return
     */
    List<Article> selectOfficialArticles(String auditState,Integer officialArticleNumber);

    /**
     * 获取当前最热（阅读最多）的前 hotArticleLimit 篇文章
     * @param auditState 被选取的审核状态
     * @param hotArticleLimit
     * @return
     */
    List<Article> selectHotArticle(String auditState,int hotArticleLimit);

    /**
     * 获取当前最新（日期最新）的前 newArticleLimit 篇文章
     * @param auditState 被选取的审核状态
     * @param newArticleLimit
     * @return
     */
    List<Article> selectNewArticle(String auditState,int newArticleLimit);

    /**
     * 将 articleId 对应的文章 进行逻辑删除
     * @param articleId 文章id
     * @return
     */
    int deleteArticleById(Long articleId);

    /**
     * 获取当前最大的主键id 值
     * @return
     */
    Long selectMaxId();

    /**
     * 根据 圈子id、逻辑删除、是否已读 获取文章集合
     * @param auditState 被选取的审核状态
     * @param categoryId 圈子id
     * @param visitPermission 阅读权限不能是该权限
     * @return 文章集合
     */
    List<Article> selectArticlesByCategoryId(String auditState,String visitPermission,Integer categoryId);
}

