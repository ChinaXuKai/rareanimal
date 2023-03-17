package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.vo.ArticleCategoriesVo;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-07 17:05
 */
public interface CategoryService {

    /**
     * 获取所有的文章分类
     * @return
     */
    List<ArticleCategoriesVo> findArticleCategories();
}
