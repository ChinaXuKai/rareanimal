package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.QuestionTag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-04-04 8:34
 */
@Repository
public interface QuestionTagMapper extends BaseMapper<QuestionTag> {
    /**
     * 根据问题id 查询对应的 问题标签集合
     * @param questionId 问题id
     * @return 问题标签集合
     */
    List<String> selectTagsById(Long questionId);

}
