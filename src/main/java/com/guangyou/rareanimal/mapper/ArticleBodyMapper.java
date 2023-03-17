package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.ArticleBody;
import com.guangyou.rareanimal.pojo.vo.ArticleBodyVo;
import org.springframework.stereotype.Repository;

/**
 * @author xukai
 * @create 2023-01-02 15:48
 */
@Repository
public interface ArticleBodyMapper extends BaseMapper<ArticleBody> {

    ArticleBodyVo findArticleBodyById(Long bodyId);
}
