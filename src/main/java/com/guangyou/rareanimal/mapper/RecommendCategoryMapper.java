package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.RecommendCategory;
import com.guangyou.rareanimal.pojo.vo.CategoryVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-03-09 16:23
 */
@Repository
public interface RecommendCategoryMapper extends BaseMapper<RecommendCategory> {

    void deleteAll();

    //根据推荐系数获取 recommendArticleCategoryNumber 个文章圈子
    List<RecommendCategory> getRecommendCategory(Integer recommendArticleCategoryNumber);
}
