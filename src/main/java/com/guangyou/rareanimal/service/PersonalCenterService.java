package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.ArticleVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;
import com.guangyou.rareanimal.pojo.vo.UserCarerVo;

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
    PageDataVo<ArticleVo> getMyArticles(PageDto pageDto,String userAccount);

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

    /**
     * 用户userId 查看收藏文章列表
     * @param pageDto
     * @param userId
     * @return
     */
    PageDataVo<ArticleVo> listSaveArticles(PageDto pageDto, Integer userId);
}
