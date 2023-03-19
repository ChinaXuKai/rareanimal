package com.guangyou.rareanimal.mapper;

import com.guangyou.rareanimal.pojo.Article;
import com.guangyou.rareanimal.pojo.User;
import com.guangyou.rareanimal.pojo.vo.ArticleVo;
import com.guangyou.rareanimal.pojo.vo.CaredBloggerVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-21 22:48
 */
@Repository
public interface PersonalCenterMapper {
    /**
     * 根据用户账号 查询他的所有文章
     * @param userAccount
     * @return
     */
    List<Article> selectMyArticles(String userAccount, Integer initialDataLocation, Integer pageSize);

    /**
     * 根据authorId 查询他的粉丝数
     * @param authorId
     * @return
     */
    int selectMyFansCounts(Long authorId);

    /**
     * 根据userId 获取博主信息
     * @param carerId
     * @return
     */
    User selectCarerByUserId(Long carerId);
}
