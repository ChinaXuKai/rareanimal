package com.guangyou.rareanimal.utils;

import com.guangyou.rareanimal.mapper.ArticleMapper;
import com.guangyou.rareanimal.mapper.UserCarerMapper;
import com.guangyou.rareanimal.mapper.UserMapper;
import com.guangyou.rareanimal.pojo.Article;
import com.guangyou.rareanimal.pojo.User;
import com.guangyou.rareanimal.pojo.UserCarer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xukai
 * @create 2023-03-17 14:04
 */
@Component
public class ArticleUtil {
    public final static String VISIT_PERMISSION_ALL = "全部可见";
    public final static String VISIT_PERMISSION_CARER = "关注可见";
    public final static String VISIT_PERMISSION_MY = "仅我可见";

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCarerMapper userCarerMapper;
    /**
     * 判断该用户 是否有权限访问该文章，没有则不允许用户阅读、点赞、收藏、评论该文章
     * @param userId 用户id
     * @param articleId 文章id
     * @return
     */
    public boolean haveArticleVisitPermission(Long userId,Long articleId){
        //1、先从 t_article 中查询该文章的访问权限
        String visitPermission = articleMapper.selectVisitPermission(articleId);
        //若访问权限为仅我可见
        if (VISIT_PERMISSION_MY.equals(visitPermission)){
            //查看该用户是否就是该文章作者
            Article article = articleMapper.selectById(articleId);
            String authorAccount = article.getAuthorAccount();
            String userAccount = userMapper.selectById(userId).getUserAccount();
            //该用户不是该文章作者
            if (!userAccount.equals(authorAccount)){
                return false;
            }
        }
        //若访问权限为关注可见
        if (VISIT_PERMISSION_CARER.equals(visitPermission)){
            //查看该用户是否是该文章作者的粉丝
            String authorAccount = articleMapper.selectById(articleId).getAuthorAccount();
            UserCarer userCarer = userCarerMapper.selectCarerById(userId,authorAccount);
            //若userCarer 为 null，则说明该用户不是该文章作者的粉丝
            return userCarer != null;
        }
        //若访问权限为全部可见，则直接返回true
        return true;
    }


}
