package com.guangyou.rareanimal.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guangyou.rareanimal.mapper.*;
import com.guangyou.rareanimal.pojo.*;
import com.guangyou.rareanimal.pojo.vo.ArticleVo;
import com.guangyou.rareanimal.pojo.vo.AuthorInfoVo;
import com.guangyou.rareanimal.pojo.vo.CategoryVo;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xukai
 * @create 2023-03-17 14:04
 */
@Component
public class ArticleUtil {
    public final static String VISIT_PERMISSION_ALL = "全部可见";
    public final static String VISIT_PERMISSION_CARER = "关注可见";
    public final static String VISIT_PERMISSION_MY = "仅我可见";

    public final static int NOT_FOUND_ARTICLE = -1;
    public final static int NOT_PERMISSION_VIEW_ARTICLE = 0;
    public final static int HAVE_PERMISSION_VIEW_ARTICLE = 1;

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
//        if (visitPermission.)
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


    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCoverImgMapper articleCoverImgMapper;
    @Autowired
    private CustomTagMapper customTagMapper;
    @Autowired
    private PersonalCenterMapper personalCenterMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public List<ArticleVo> copyList(Integer userId,List<Article> articleList, boolean isCreateTime, boolean isTag, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articleList){
            articleVoList.add(copy(userId,article,isCreateTime,isTag,isBody,isCategory));
        }
        return articleVoList;
    }
    public ArticleVo copy(Integer userId,Article article,boolean isCreateTime,boolean isTag,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        /**
         * createDate、authorAccount、authorAvatarUrl、authorName、tag、category、body、coverImg都不能被复制属性，所以要单独拿出来赋值
         * 注：weight 也不能被赋值，因为要考虑到官方发表的文章被查看，
         *          官方发表的weight == 1，而默认发表的weight == 0，因此官方文章的weight需要重新赋值
         */
        if (User.OFFICIAL_ACCOUNT.equals(article.getAuthorAccount()) ){
            articleVo.setWeight(1);
        }
        //可以先根据article 的authorAccount 查询出 具体用户信息，从中可获取用户昵称
        List<User> userList = userMapper.getUsersByAccount(article.getAuthorAccount());
        for (User user : userList){
            String existUserAccount = user.getUserAccount();
            //若数据库中已存在的用户账号与该文章发表的用户账号大小写完全一致的话，则就是该用户
            if (existUserAccount.equals(article.getAuthorAccount())){
                AuthorInfoVo authorInfoVo = new AuthorInfoVo();
                articleVo.setAuthorInfo(authorInfoVo);
                //设置 ArticleVo 的属性：作者id、作者名称、作者账号、作者头像、粉丝数、关注数、社交动态数、注册时间、是否已关注
                articleVo.getAuthorInfo().setAuthorId(user.getUserId().longValue());
                articleVo.getAuthorInfo().setAuthorName(user.getUserName());
                articleVo.getAuthorInfo().setAuthorAccount(user.getUserAccount());
                articleVo.getAuthorInfo().setAuthorAvatarUrl(user.getUserAvatar());
                //粉丝数：统计 t_user_carer 表中 carer_id 等于该用户id 的数量
                articleVo.getAuthorInfo().setFansCount(personalCenterMapper.selectMyFansCounts(user.getUserId().longValue()));
                //关注数：统计 t_user_carer 表中 user_id 等于该用户id 的数量
                LambdaQueryWrapper<UserCarer> userCarerQueryWrapper = new LambdaQueryWrapper<>();
                userCarerQueryWrapper.eq(UserCarer::getUserId, user.getUserId());
                articleVo.getAuthorInfo().setCareCount(userCarerMapper.selectCount(userCarerQueryWrapper).intValue());
                //社交动态数：统计 t_article 表中 author_account 等于该用户账号 的数量，
                //              加上 t_question 表中 user_id 等于该用户id 的数量
                LambdaQueryWrapper<Article> articleQueryWrapper = new LambdaQueryWrapper<>();
                articleQueryWrapper.eq(Article::getAuthorAccount, user.getUserAccount());
                int articleCount = articleMapper.selectCount(articleQueryWrapper).intValue();
                LambdaQueryWrapper<Question> questionQueryWrapper = new LambdaQueryWrapper<>();
                questionQueryWrapper.eq(Question::getUserId, user.getUserId());
                int questionCount = questionMapper.selectCount(questionQueryWrapper).intValue();
                articleVo.getAuthorInfo().setSocialCount(articleCount + questionCount);
                //注册时间：
                articleVo.getAuthorInfo().setCreateTime(new DateTime(user.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"));
                //是否已关注：若当前用户id 非空
                if (userId != null){
                    int caredCount = userCarerMapper.selectCountById(userId.longValue(),articleVo.getAuthorInfo().getAuthorId());
                    articleVo.getAuthorInfo().setIsCared(caredCount);
                }else {
                    articleVo.getAuthorInfo().setIsCared(0);
                }
            }
        }
        //coverImg：根据articleId 从t_article_cover_img 中 获取coverImg 集合
        List<String> coverImgs = new ArrayList<>();
        LambdaQueryWrapper<ArticleCoverImg> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleCoverImg::getArticleId, article.getId());
        List<ArticleCoverImg> articleCoverImgs = articleCoverImgMapper.selectList(queryWrapper);

        for (ArticleCoverImg articleCoverImg : articleCoverImgs){
            coverImgs.add(articleCoverImg.getCoverImg());
        }
        articleVo.setCoverImg(coverImgs);
        //不是所有的接口 都需要 tag
        if (isTag){     //若需要 tag 属性
            Long articleId = article.getId();
            articleVo.setTags(customTagMapper.selectTagsByArticleId(articleId));
        }
        //若需要从数据库查询时间则从数据库中查询，否则设置为当前时间
        if (isCreateTime){
            Long articleId = article.getId();
            String articleCreateTime = new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss");
            articleVo.setCreateDate(articleCreateTime);
        }else {
            articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(articleBodyMapper.findArticleBodyById(bodyId));
        }
        if (isCategory){
            CategoryVo categoryVo = new CategoryVo();
            Long categoryId = article.getCategoryId();
            Category articleCategory = categoryMapper.findCategoryById(categoryId);
            BeanUtils.copyProperties(articleCategory, categoryVo);
            articleVo.setCategory(categoryVo);
        }
        return articleVo;
    }


}
