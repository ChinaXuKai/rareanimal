package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guangyou.rareanimal.mapper.*;
import com.guangyou.rareanimal.pojo.*;
import com.guangyou.rareanimal.pojo.vo.*;
import com.guangyou.rareanimal.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xukai
 * @create 2023-02-10 10:45
 */
@Slf4j
@Service
//@EnableScheduling
public class RecommendServiceImpl implements RecommendService {

    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private RecommendCategoryMapper recommendCategoryMapper;


    private void copyCategory(List<RecommendCategory> dbRecommendCategories,List<RecommendCategoryVo> recommendCategoryVos){
        for (int i = 0;i < dbRecommendCategories.size();i++){
            RecommendCategory dbCategory = dbRecommendCategories.get(i);
            RecommendCategoryVo categoryVo = new RecommendCategoryVo();
            BeanUtils.copyProperties(dbRecommendCategories.get(i), categoryVo);
            categoryVo.setRecommendTime(new DateTime(dbCategory.getRecommendTime()).toString("yyyy-MM-dd"));
            recommendCategoryVos.add(categoryVo);
        }
    }
    /**
     * 计算 所有圈子的推荐系数 ，封装成RecommendCategory类型的list对象并添加进行 t_recommend_category 表中
     * 获取到 推荐文章圈子集合 ，将该集合 存入 MySQL，记录 存入时间
     * @param recommendArticleCategoryNumber 获取推荐文章圈子所的个数
     */
    private List<RecommendCategory> acquireRecommendCategory(Integer recommendArticleCategoryNumber){
        //1、计算 所有圈子的推荐系数 ，封装成RecommendCategory类型的list对象并添加进行 t_recommend_category 表中
        computeAndInsertCategoriesRecommendFactor();
        //获取到 推荐文章圈子集合 articleCategories
        return recommendCategoryMapper.getRecommendCategory(recommendArticleCategoryNumber);
    }
    /**
     * 计算 所有圈子的推荐系数 ，封装成RecommendCategory类型的list对象并添加进行 t_recommend_category 表中
     * 推荐系数 = 所属该文章分类的总文章数
     */
    private void computeAndInsertCategoriesRecommendFactor(){
        List<RecommendCategory> recommendCategories = new ArrayList<>();
        //1、获取到 圈子集合
        List<Category> categories = categoryMapper.selectList(null);
        //2、for循环 依次封装对象并添加进数据库
        for (int i = 0; i < categories.size(); i++){
            //获取相应的 category对象，添加并获取recommendCategory对象
            Category category = categories.get(i);
            recommendCategories.add(new RecommendCategory());
            RecommendCategory recommendCategory = recommendCategories.get(i);
            //封装recommendCategory对象并添加
            int articleCount = categoryMapper.selectArticleCount(category.getId());
            recommendCategory.setRecommendFactor(articleCount);
            recommendCategory.setCategoryId(category.getId());
            recommendCategory.setCategoryName(category.getCategoryName());
            recommendCategory.setCategoryLabel(category.getCategoryLabel());
            recommendCategory.setArticleCount(articleCount);
            recommendCategory.setRecommendTime(System.currentTimeMillis());
            recommendCategory.setCategoryAvatar(category.getCategoryAvatar());
            recommendCategoryMapper.insert(recommendCategory);
        }
    }
    /**
     * 需求：返回 推荐文章圈子集合 ，该集合只能一天获取一次（在一天内，第一次获取后，后续访问接口所获取的数据不能改变）
     *          且 该集合 在所有用户的页面显示皆一致（该集合与用户无关）
     * 1、获取当前系统时间
     * 2、先判断 t_recommend_category 表中是否有数据：
     *      无数据：
     *          计算所有圈子的推荐系数，并添加进 t_recommend_category 表中；
     *          获取到 推荐文章圈子集合 ，将该集合 存入 MySQL，记录 存入时间
     *      有数据：
     *          判断当前系统时间 与 数据库时间 是否是同一天
     *              是同一天：直接从 t_recommend_category 表中获取的数据
     *              不是同一天：先删除原有数据，计算所有圈子的推荐系数，并添加进 t_recommend_category 表中；
     *                         获取到 推荐文章圈子集合 ，将 t_recommend_category 表中的数据做修改
     */
    @Override
    public List<RecommendCategoryVo> getRecommendArticleCategory(Integer recommendArticleCategoryNumber) {
        List<RecommendCategoryVo> recommendCategoryVos = new ArrayList<>();
        //1、获取当前系统日期
        String recommendTime = new DateTime(System.currentTimeMillis()).toString("yyyy-MM-dd");
        //2、先判断 t_recommend_category 表中是否有数据：
        boolean isExist = recommendCategoryMapper.exists(null);
        if (!isExist){      //无数据
            List<RecommendCategory> dbRecommendCategories = acquireRecommendCategory(recommendArticleCategoryNumber);
            copyCategory(dbRecommendCategories, recommendCategoryVos);
            return recommendCategoryVos;
        }else {             //有数据
            List<RecommendCategory> dbRecommendCategories = recommendCategoryMapper.selectList(null);
            String saveTime = new DateTime(dbRecommendCategories.get(0).getRecommendTime()).toString("yyyy-MM-dd");
            if (saveTime.equals(recommendTime)){        //日期一致，直接取数据
                dbRecommendCategories = recommendCategoryMapper.getRecommendCategory(recommendArticleCategoryNumber);
                copyCategory(dbRecommendCategories, recommendCategoryVos);
                return recommendCategoryVos;
            }else {                                     //日期不一致，删除原有的数据，重新添加并获取
                recommendCategoryMapper.deleteAll();
                dbRecommendCategories = acquireRecommendCategory(recommendArticleCategoryNumber);
                copyCategory(dbRecommendCategories, recommendCategoryVos);
                return recommendCategoryVos;
            }
        }
    }


    @Autowired
    private RecommendUserMapper recommendUserMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserCarerMapper userCarerMapper;
    @Autowired
    private ArticleMapper articleMapper;
    /**
     * 需求：返回 推荐用户集合 ，该集合每分钟获取一次（在每小时内，第一次获取后，后续访问接口所获取的数据不能改变）
     *          且 该集合 在所有用户的页面显示皆一致（该集合与用户无关）
     * 1、获取当前系统时间
     * 2、先判断 t_recommend_user 表中是否有数据：
     *      无数据：
     *          获取到 推荐用户集合 ，将该集合 存入 MySQL，记录 存入时间
     *      有数据：
     *          判断当前系统时间 与 数据库时间 是否是同一分钟
     *              是同一小时：直接从 t_recommend_user 表中获取的数据
     *              不是同一小时：获取到 推荐用户集合 ，将 t_recommend_user 表中的数据做修改
     */
    @Override
    public List<RecommendUserVo> getRecommendUser(Integer recommendUserNumber) {
        List<RecommendUserVo> recommendUserVos = new ArrayList<>();
        //1、获取当前系统日期
        Long recommendTimeL = System.currentTimeMillis();
        String recommendTimeS = new DateTime(recommendTimeL).toString("yyyy-MM-dd HH");
        //2、先判断 t_recommend_category 表中是否有数据：
        boolean isExist = recommendUserMapper.exists(null);
        if (!isExist){      //无数据
            List<RecommendUser> dbRecommendUsers = acquireRecommendUser(recommendUserNumber);
            copyUser(dbRecommendUsers, recommendUserVos,recommendTimeS);
            return recommendUserVos;
        }else {             //有数据
            List<RecommendUser> dbRecommendUsers = recommendUserMapper.selectList(null);
            String saveTime = new DateTime(dbRecommendUsers.get(0).getRecommendTime()).toString("yyyy-MM-dd HH");
            if (saveTime.equals(recommendTimeS)){        //小时一致，直接取数据
                dbRecommendUsers = recommendUserMapper.getRecommendUser(recommendUserNumber);
                copyUser(dbRecommendUsers, recommendUserVos,recommendTimeS);
                return recommendUserVos;
            }else {                                     //小时不一致
                //删除原有的数据
                recommendUserMapper.delete(null);
                dbRecommendUsers = acquireRecommendUser(recommendUserNumber);
                copyUser(dbRecommendUsers, recommendUserVos,recommendTimeS);
                return recommendUserVos;
            }
        }
    }
    private void copyUser(List<RecommendUser> dbRecommendUsers, List<RecommendUserVo> recommendUserVos,String recommendTime) {
        for (int i = 0;i <dbRecommendUsers.size(); i++){
            RecommendUserVo recommendUserVo = new RecommendUserVo();
            UserInfoVo recommendUserInfo = new UserInfoVo();
            RecommendUser dbUser = dbRecommendUsers.get(i);
            BeanUtils.copyProperties(dbUser,recommendUserVo);
            //为 recommendUserVo 赋值，最后添加进 recommendUserVos
            User user = userMapper.getUsersByUid(dbUser.getUserId());
            recommendUserInfo.setUserAccount(user.getUserAccount());
            recommendUserInfo.setCreateTime(new DateTime(user.getCreateTime()).toString("yyyy-MM-dd HH:mm"));
            recommendUserInfo.setFansCount(userCarerMapper.getCarerCountByUserId(dbUser.getUserId()));
            recommendUserInfo.setUserId(dbUser.getUserId());
            recommendUserInfo.setUserName(dbUser.getUserName());
            recommendUserInfo.setUserAvatar(dbUser.getUserAvatar());
            recommendUserVo.setRecommendUserInfo(recommendUserInfo);
            recommendUserVo.setRecommendTime(recommendTime);
            recommendUserVos.add(recommendUserVo);
        }
    }
    private List<RecommendUser> acquireRecommendUser(Integer recommendUserNumber) {
        computeAndInsertUsersRecommendFactor();
        //获取到 推荐文章圈子集合 articleCategories
        return recommendUserMapper.getRecommendUser(recommendUserNumber);
    }
    /**
     * 计算 所有用户的推荐系数 ，封装成RecommendUser类型的list对象并添加进行 t_recommend_user 表中
     * 推荐系数 =（粉丝数*0.5 + 发表文章数*0.5） * 10
     */
    private void computeAndInsertUsersRecommendFactor(){
        List<RecommendUser> recommendUsers = new ArrayList<>();
        //1、获取到 圈子集合
        List<User> userList = userMapper.selectList(null);
        //2、for循环 依次封装对象并添加进数据库
        for (int i = 0; i < userList.size(); i++){
            //获取相应的 category对象，添加并获取recommendCategory对象
            User user = userList.get(i);
            recommendUsers.add(new RecommendUser());
            RecommendUser recommendUser = recommendUsers.get(i);
            //封装recommendCategory对象并添加
            recommendUser.setUserId(user.getUserId().longValue());
            recommendUser.setUserName(user.getUserName());
            recommendUser.setUserAvatar(user.getUserAvatar());
            recommendUser.setRecommendTime(System.currentTimeMillis());
            int recommendFactor = (int) ((userCarerMapper.getCarerCountByUserId(user.getUserId().longValue()) * 0.5
                                                + articleMapper.getArticleCountByUser(user.getUserAccount()) * 0.5 ) * 10);
            recommendUser.setRecommendFactor(recommendFactor);
            recommendUserMapper.insert(recommendUser);
        }
    }


    @Autowired
    private ArticleCoverImgMapper articleCoverImgMapper;
    @Autowired
    private RecommendArticleMapper recommendArticleMapper;
    /**
     * 需求：返回 推荐文章集合
     * 1、获取当前系统时间
     * 2、先判断 t_recommend_article 表中是否有数据：
     *      无数据：
     *          获取到 推荐用户集合 ，将该集合 存入 MySQL，记录 存入时间
     *      有数据：
     *              删除原有数据，重新获取到 推荐用户集合 ，将 t_recommend_article 表中的数据做修改
     */
    @Override
    public List<RecommendArticleVo> getRecommendArticle(Integer recommendArticleNumber) {
        List<RecommendArticleVo> recommendArticleVos = new ArrayList<>();
        //1、获取当前系统日期
        String recommendTime = new DateTime(System.currentTimeMillis()).toString("yyyy-MM-dd HH:mm");
        //2、先判断 t_recommend_article 表中是否有数据：
        boolean isExist = recommendArticleMapper.exists(null);
        if (!isExist){      //无数据
            List<RecommendArticle> dbRecommendArticles = acquireRecommendArticle(recommendArticleNumber);
            copyArticle(dbRecommendArticles, recommendArticleVos,recommendTime);
            return recommendArticleVos;
        }else {             //有数据
            List<RecommendArticle> dbRecommendArticles = recommendArticleMapper.selectList(null);
            //删除原有的数据
            recommendArticleMapper.delete(null);
            dbRecommendArticles = acquireRecommendArticle(recommendArticleNumber);
            copyArticle(dbRecommendArticles, recommendArticleVos,recommendTime);
            return recommendArticleVos;
        }
    }
    private void copyArticle(List<RecommendArticle> dbRecommendArticles, List<RecommendArticleVo> recommendArticleVos,String recommendTime) {
        for (int i = 0;i <dbRecommendArticles.size(); i++){
            RecommendArticleVo recommendArticleVo = new RecommendArticleVo();
            RecommendArticle dbArticle = dbRecommendArticles.get(i);
            BeanUtils.copyProperties(dbArticle,recommendArticleVo);
            recommendArticleVo.setRecommendTime(recommendTime);
            //title、authorName、saveCounts、supportCounts、viewCounts、commentCounts、coverImg
            Long articleId = dbArticle.getArticleId();
            Article article = articleMapper.selectById(articleId);
            recommendArticleVo.setTitle(article.getTitle());
            User author = userMapper.getUsersByAccount(article.getAuthorAccount()).get(0);
            AuthorInfoVo authorInfoVo = new AuthorInfoVo();
            recommendArticleVo.setAuthorInfo(authorInfoVo);
            recommendArticleVo.getAuthorInfo().setAuthorName(author.getUserName());
            recommendArticleVo.getAuthorInfo().setAuthorAccount(author.getUserAccount());
            recommendArticleVo.getAuthorInfo().setAuthorId(author.getUserId().longValue());
            recommendArticleVo.getAuthorInfo().setAuthorAvatarUrl(author.getUserAvatar());
            recommendArticleVo.setSaveCounts(article.getSaveCounts());
            recommendArticleVo.setSupportCounts(article.getSupportCounts());
            recommendArticleVo.setViewCounts(article.getViewCounts());
            recommendArticleVo.setCommentCounts(article.getCommentCounts());
            List<String> coverImg = articleCoverImgMapper.selectCoverImgByArticleId(articleId);
            recommendArticleVo.setCoverImg(coverImg);
            recommendArticleVos.add(recommendArticleVo);
        }
    }
    private List<RecommendArticle> acquireRecommendArticle(Integer recommendUserNumber) {
        computeAndInsertArticlesRecommendFactor();
        //获取到 推荐文章圈子集合 articleCategories
        return recommendArticleMapper.getRecommendArticles(recommendUserNumber);
    }
    /**
     * 计算 所有文章的推荐系数 ，封装成RecommendUser类型的list对象并添加进行 t_recommend_user 表中
     * 推荐系数 =（ （点赞数/阅读数）* 0.3 + 评论数*0.3 + 收藏数*0.4 ） *100
     */
    private void computeAndInsertArticlesRecommendFactor(){
        List<RecommendArticle> recommendArticles = new ArrayList<>();
        //1、获取到 非官方没逻辑删除 的文章集合
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getIsDelete, 0);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        if (articles.size() == 0){

        }
        //2、for循环 依次封装对象并添加进数据库
        for (int i = 0; i < articles.size(); i++){
            //获取相应的 article对象，添加并获取recommendArticle对象
            Article article = articles.get(i);
            recommendArticles.add(new RecommendArticle());
            RecommendArticle recommendArticle = recommendArticles.get(i);
            //封装recommendCategory对象并添加
            recommendArticle.setArticleId(article.getId());
            recommendArticle.setRecommendTime(System.currentTimeMillis());
            int recommendFactor = (int) ( ( (article.getViewCounts() == 0? 0 : article.getSupportCounts()/article.getViewCounts())*0.3 +
                                                    article.getCommentCounts()*0.3 + article.getSaveCounts()*0.4) *100);
            recommendArticle.setRecommendFactor(recommendFactor);
            recommendArticleMapper.insert(recommendArticle);
        }
    }


}
