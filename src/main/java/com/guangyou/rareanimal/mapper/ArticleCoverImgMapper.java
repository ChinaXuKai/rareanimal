package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.ArticleCoverImg;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-03-17 17:25
 */
@Repository
public interface ArticleCoverImgMapper extends BaseMapper<ArticleCoverImg> {

    /**
     * 根据文章id 查询对应的 封面url地址集合
     * @param articleId 文章id
     * @return
     */
    List<String> selectCoverImgByArticleId(Long articleId);

}
