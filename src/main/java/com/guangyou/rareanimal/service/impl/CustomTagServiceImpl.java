package com.guangyou.rareanimal.service.impl;

import com.guangyou.rareanimal.mapper.CustomTagMapper;
import com.guangyou.rareanimal.pojo.CustomTag;
import com.guangyou.rareanimal.pojo.vo.CustomTagVo;
import com.guangyou.rareanimal.service.CustomTagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xukai
 * @create 2023-01-01 15:19
 */
@Service
public class CustomTagServiceImpl implements CustomTagService {

    @Autowired
    private CustomTagMapper customTagMapper;

    @Override
    public List<CustomTagVo> getHotTagsInfo(int limit) {
        return customTagMapper.selectHotTags(limit);
    }

    @Override
    public List<CustomTagVo> findTagsByArticleId(Long articleId) {
        return customTagMapper.selectTagsByArticleId(articleId);
    }

    @Override
    public List<CustomTagVo> findArticleTags() {
        return customTagMapper.selectAllTags();
    }

    private List<CustomTagVo> copyList(List<CustomTag> tags) {
        List<CustomTagVo> tagVos = new ArrayList<>();
        for (CustomTag tag : tags){
            tagVos.add(copy(tag));
        }
        return tagVos;
    }
    private CustomTagVo copy(CustomTag tag){
        CustomTagVo tagVo = new CustomTagVo();
        BeanUtils.copyProperties(tag, tagVo);
        tagVo.setCustomTagName(tag.getCustomTagName());
        tagVo.setId(tag.getId());
        return tagVo;
    }

}
