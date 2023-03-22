package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.CategoryTheme;
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

    /**
     * 获取所有的文章圈子主题
     * @return
     */
    List<CategoryTheme> getAllTheme();

    /**
     * 获取主题id 对应的文章圈子集合
     * @param themeId
     * @return
     */
    ArticleCategoriesVo getCategoryByThemeId(Long themeId);
}
