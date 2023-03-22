package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.Category;
import com.guangyou.rareanimal.pojo.CategoryTheme;
import com.guangyou.rareanimal.pojo.vo.ArticleCategoriesVo;
import com.guangyou.rareanimal.pojo.vo.CategoryVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-02 18:10
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    //根据 categoryId 查询 CategoryVo信息
    Category findCategoryById(Long categoryId);

    //查询所有的文章分类
    List<CategoryVo> selectAllArticleCategories();

    //查询所有文章圈子的主题
    List<CategoryTheme> selectAllCategoryTheme();

    //根据文章圈子主题的id查询对应文章圈子
    List<CategoryVo> selectCategoryByThemeId(Long themeId);

    //根据圈子id查询 对应的文章数量
    int selectArticleCount(Long categoryId);

    //根据主题id查询 对应主题信息
    CategoryTheme selectThemeByThemeId(Long themeId);
}
