package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.vo.CustomTagVo;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-01 15:19
 */
public interface CustomTagService {

    //根据文章Id查询文章标签
    List<CustomTagVo> findTagsByArticleId(Long articleId);

    //根据所有最热标签的id 查询所有最热标签的信息
    List<CustomTagVo> getHotTagsInfo(int limit);

    //查询所有的文章标签
    List<CustomTagVo> findArticleTags();

}
