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
     * 随机获取 randArticleNumber 个文章
     * @param randArticleNumber 获取个数
     * @return
     */
    List<Article> selectRandArticles(Integer randArticleNumber);
}

