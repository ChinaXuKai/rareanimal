package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.vo.CategoryVo;
import com.guangyou.rareanimal.pojo.vo.RecommendArticleVo;
import com.guangyou.rareanimal.pojo.vo.RecommendCategoryVo;
import com.guangyou.rareanimal.pojo.vo.RecommendUserVo;

import java.util.List;

/**
 * @author xukai
 * @create 2023-02-10 10:45
 */
public interface RecommendService {

    /**
     * 返回 recommendArticleCategoryNumber 个文章圈子作为推荐文章圈子集合
     * @param recommendArticleCategoryNumber 推荐个数
     * @return
     */
    List<RecommendCategoryVo> getRecommendArticleCategory(Integer recommendArticleCategoryNumber);

    /**
     * 返回 recommendUserNumber 个用户作为推荐用户集合
     * @param recommendUserNumber 推荐个数
     * @param userId 当前用户id
     * @return
     */
    List<RecommendUserVo> getRecommendUser(Integer recommendUserNumber,Integer userId);

    /**
     * 返回 recommendArticleNumber 个文章作为推荐文章集合
     * @param recommendArticleNumber 推荐个数
     * @return
     */
    List<RecommendArticleVo> getRecommendArticle(Integer recommendArticleNumber);
}
