package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.RecommendUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-03-10 10:34
 */
@Repository
public interface RecommendUserMapper extends BaseMapper<RecommendUser> {

    /**
     * 根据推荐系数获取 recommendUserNumber 个用户
     * @param recommendUserNumber
     * @return
     */
    List<RecommendUser> getRecommendUser(Integer recommendUserNumber);

}
