package com.guangyou.rareanimal.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.guangyou.rareanimal.mapper.ArticleMapper;
import com.guangyou.rareanimal.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author xukai
 * @create 2023-01-02 18:33
 */
@Component
public class ThreadService {


    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper,Article article){
        //update t_article set view_counts = #{view_counts+1} where view_counts = #{view_counts} and id = #{id}
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        updateWrapper.eq(Article::getViewCounts, viewCounts);
        articleMapper.update(articleUpdate, updateWrapper);
    }
}
