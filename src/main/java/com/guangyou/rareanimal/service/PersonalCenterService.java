package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.ArticleVo;
import com.guangyou.rareanimal.pojo.vo.UserCarerVo;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-21 22:39
 */
public interface PersonalCenterService {

    /**
     * 用户userAccount 获取所发表过的所有文章
     * @param userAccount
     * @return
     */
    List<ArticleVo> getMyArticles(String userAccount);

    /**
     * 根据authorId 查询粉丝数
     * @param authorId
     * @return
     */
    int getMyFansCounts(Integer authorId);

    /**
     * 根据userId 查询所关注的用户
     * @param userId
     * @return
     */
    UserCarerVo getMyCarers(PageDto pageDto, Integer userId);
}
