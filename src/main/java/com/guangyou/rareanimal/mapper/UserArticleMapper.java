package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.UserArticle;
import org.springframework.stereotype.Repository;

/**
 * @author xukai
 * @create 2023-01-11 11:32
 */
@Repository
public interface UserArticleMapper extends BaseMapper<UserArticle> {

    /**
     *
     * @param userId
     * @return
     */
    int selectSaveArticleCount(Integer userId);
}
