package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.RecommendArticle;
import com.guangyou.rareanimal.pojo.RecommendUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-03-16 8:15
 */
@Repository
public interface RecommendArticleMapper extends BaseMapper<RecommendArticle> {

    /**
     * 根据推荐系数从大到小获取前 recommendUserNumber 个推荐文章
     * @param recommendUserNumber 推荐个数
     * @return
     */
    List<RecommendArticle> getRecommendArticles(Integer recommendUserNumber);
}
