package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guangyou.rareanimal.mapper.ArticleMapper;
import com.guangyou.rareanimal.pojo.Article;
import com.guangyou.rareanimal.pojo.Category;
import com.guangyou.rareanimal.pojo.CategoryTheme;
import com.guangyou.rareanimal.pojo.vo.ArticleCategoriesVo;
import com.guangyou.rareanimal.pojo.vo.CategoryVo;
import com.guangyou.rareanimal.service.CategoryService;
import com.guangyou.rareanimal.mapper.CategoryMapper;
import com.guangyou.rareanimal.shiro.AccountProfile;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xukai
 * @create 2023-01-07 17:06
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleMapper articleMapper;

    @Transactional
    @Override
    public List<ArticleCategoriesVo> findArticleCategories() {
        List<ArticleCategoriesVo> articleCategoriesVos = new ArrayList<>();
        List<CategoryVo> categoryVos = null;
        //通过 t_article表 重新查询每个圈子有多少篇文章（is_delete=0），并设置到 t_category表 中的 article_count字段
        List<Category> categoryList = categoryMapper.selectList(null);
        for (Category category : categoryList){
            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Article::getCategoryId, category.getId());
            queryWrapper.eq(Article::getIsDelete, 0);
            int articleCount = articleMapper.selectCount(queryWrapper).intValue();
            categoryMapper.updateArticleCount(articleCount,category.getId());
        }
        //查询到所有的文章圈子主题
        List<CategoryTheme> categoryThemes = categoryMapper.selectAllCategoryTheme();
        for (int i = 0; i < categoryThemes.size(); i++){
            //每有一个文章圈子主题，就在 articleCategoriesVos 中添加一个元素 articleCategoriesVo
            CategoryTheme categoryTheme = categoryThemes.get(i);
            articleCategoriesVos.add(new ArticleCategoriesVo());
            ArticleCategoriesVo articleCategoriesVo = articleCategoriesVos.get(i);
            //通过 该圈子主题id 查询 对应所有的文章圈子，然后为 articleCategoriesVo 赋值
            articleCategoriesVo.setTheme(categoryTheme.getThemeName());
            articleCategoriesVo.setThemeId(categoryTheme.getId());
            //通过 t_article表 重新查询每个圈子有多少篇文章
            categoryVos = categoryMapper.selectCategoryByThemeId(categoryTheme.getId());
            articleCategoriesVo.setArticleCategories(categoryVos);
        }
        return articleCategoriesVos;
    }

    @Override
    public List<CategoryTheme> getAllTheme() {
        return categoryMapper.selectAllCategoryTheme();
    }


    @Override
    public ArticleCategoriesVo getCategoryByThemeId(Long themeId) {
        ArticleCategoriesVo articleCategoriesVo = new ArticleCategoriesVo();
        List<CategoryVo> categoryVos = categoryMapper.selectCategoryByThemeId(themeId);
        articleCategoriesVo.setThemeId(themeId);
        CategoryTheme theme = categoryMapper.selectThemeByThemeId(themeId);
        articleCategoriesVo.setTheme(theme.getThemeName());
        articleCategoriesVo.setArticleCategories(categoryVos);
        return articleCategoriesVo;
    }

}
