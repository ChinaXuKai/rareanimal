package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.CustomTag;
import com.guangyou.rareanimal.pojo.vo.CustomTagVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-01 10:55
 */
@Repository
public interface CustomTagMapper extends BaseMapper<CustomTag> {

    /**
     * 根据文章id 查询文章标签
     * @param articleId
     * @return
     */
    List<CustomTagVo> selectTagsByArticleId(Long articleId);

    /**
     * 根据所有最热标签的id 查询最热标签信息
     * @param limit
     * @return
     */
    List<CustomTagVo> selectHotTags(int limit);

    /**
     * 查询所有的文章标签
     * @return
     */
    List<CustomTagVo> selectAllTags();
}
