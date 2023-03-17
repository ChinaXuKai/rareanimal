package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.ArticleCustomTag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-08 16:48
 */
@Repository
public interface ArticleCustomTagMapper extends BaseMapper<ArticleCustomTag> {


    List<ArticleCustomTag> selectByArticleId(Long articleId);
}
