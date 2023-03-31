package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.mapper.*;
import com.guangyou.rareanimal.pojo.*;
import com.guangyou.rareanimal.pojo.dto.ArticleDto;
import com.guangyou.rareanimal.pojo.vo.*;
import com.guangyou.rareanimal.pojo.User;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.service.ArticleService;
import com.guangyou.rareanimal.service.ThreadService;
import com.guangyou.rareanimal.utils.ArticleUtil;
import com.guangyou.rareanimal.utils.RedisUtil;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xukai
 * @create 2022-12-28 10:04
 */
@Service
public class ArticleServiceImpl implements ArticleService {


    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CustomTagMapper customTagMapper;
    @Autowired
    private ArticleCustomTagMapper articleCustomTagMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCoverImgMapper articleCoverImgMapper;

    /**
     * 为用户添加文章
     * @param articleDto
     * @return
     */
    @Override
    public Map<String, String> addArticleToUser(ArticleDto articleDto, String userAccount) {
        /*
         * 发表文章需要添加数据的表有：
         *      t_article、t_article_custom_tag、t_article_body、t_category
         *
         *
         * 1、发布文章的目的是构建 Article对象 和 添加进表 t_article
         * 2、要将 标签添加到关联表中 t_article_custom_tag
         * 3、要将 文章内容添加到关联表中
         * 4、将 封面url集合 添加到关联表 t_article_cover_img 表中
         * 5、将该文章所属的圈子的文章数加 1（t_category表的article_count字段加1）
         * 6、将数据库里的当前数据进行更新
         */
        //1、构建Article对象并添加进数据库
        Long categoryId = articleDto.getArticleBelongCategory().getCategoryId();
        Article article = new Article();
        article.setAuthorAccount(userAccount);
        article.setTitle(articleDto.getTitle());
        article.setArticleType(articleDto.getArticleType());
        article.setCategoryId(categoryId);
        article.setSummary(articleDto.getSummary());
        article.setCommentCounts(0);
        if (User.OFFICIAL_ACCOUNT.equals(userAccount)){
            article.setWeight(1);
        }else {
            article.setWeight(0);
        }
        article.setViewCounts(0);
        article.setCreateDate(System.currentTimeMillis());
        article.setBodyId(0L);
        article.setVisitPermission(articleDto.getVisitPermission());
            //先进行添加
        articleMapper.insert(article);
            //添加后可以获取到其在数据库的主键id值
        Long articleId = article.getId();
//        articleMapper.insertArticle(userAccount,0,System.currentTimeMillis(),articleDto.getSummary(),articleDto.getTitle(), 0, 0);
        //2、将标签加入到关联表中
        List<String> tagsName = articleDto.getTagsName();
        if (tagsName.size() != 0){
            for (String tagName : tagsName){
                ArticleCustomTag articleCustomTag = new ArticleCustomTag();
                articleCustomTag.setArticleId(articleId);
                articleCustomTag.setCustomTagName(tagName);
                articleCustomTagMapper.insert(articleCustomTag);
            }
        }
        //3、将文章内容加入到关联表中
        ArticleBody articleBody = new ArticleBody();
        articleBody.setArticleId(articleId);
        articleBody.setContentHtml(articleDto.getArticleBody().getContentHtml());
        articleBodyMapper.insert(articleBody);
        //设置article的bodyId属性
        article.setBodyId(articleBody.getId());
        //4、将 封面url集合 添加到关联表 t_article_cover_img 表中
        List<String> articleCoverImgUrls = articleDto.getArticleCoverImgUrls();
        for (String articleCoverImgUrl : articleCoverImgUrls){
            ArticleCoverImg articleCoverImg = new ArticleCoverImg();
            articleCoverImg.setArticleId(articleId);
            articleCoverImg.setCoverImg(articleCoverImgUrl);
            articleCoverImgMapper.insert(articleCoverImg);
        }
        //5、将该文章所属的圈子的文章数加 1（t_category表的article_count字段加1）
        LambdaUpdateWrapper<Category> updateWrapper = new LambdaUpdateWrapper<>();
        categoryMapper.update(null, updateWrapper.eq(Category::getId, categoryId).setSql("article_count = article_count + 1"));
        //6、将数据库里的当前数据进行更新
        articleMapper.updateById(article);

        HashMap<String, String> map = new HashMap<>();
        map.put("articleId", article.getId().toString());
        return map;
    }


    @Override
    public Map updateArticleToUser(ArticleDto articleDto) {
        /*
         * Article 可修改的属性：title、summary、categoryId
         * ArticleBody 可修改的属性：content、contentHtml
         * ArticleCustomTag 可修改的属性：customTagName
         * ArticleCoverImg 可修改属性：cover_img
         *      步骤：
         *          1、先修改t_article表
         *          2、从t_article表中获取 body_id
         *          3、根据 body_id 修改t_article_body表
         *          4、根据 article_id 删除 该文章原来的 customTag 集合
         *          5、获取要修改的tag集合，封装为ArticleCustomTag，添加进t_article_custom_tag表
         *          6、根据 article_id 删除 该文章原有的 coverImg 集合
         *          7、重新添加 coverImg 集合
         */
        //1、修改t_article
        Article updateArticle = new Article();
        updateArticle.setId(articleDto.getId());
        updateArticle.setTitle(articleDto.getTitle());
        updateArticle.setArticleType(articleDto.getArticleType());
        updateArticle.setCategoryId(articleDto.getArticleBelongCategory().getCategoryId());
        updateArticle.setSummary(articleDto.getSummary());
        updateArticle.setVisitPermission(articleDto.getVisitPermission());
        articleMapper.updateById(updateArticle);
        //2、获取 bodyId
        Long bodyId = articleMapper.selectById(articleDto.getId()).getBodyId();
        //3、根据 bodyId 修改t_article_body表
        ArticleBody updateArticleBody = new ArticleBody();
        updateArticleBody.setId(bodyId);
        updateArticleBody.setArticleId(articleDto.getId());
        updateArticleBody.setContentHtml(articleDto.getArticleBody().getContentHtml());
        articleBodyMapper.updateById(updateArticleBody);
        //4、获取 该文章原来的tag集合
        LambdaQueryWrapper<ArticleCustomTag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ArticleCustomTag::getArticleId, articleDto.getId());
        articleCustomTagMapper.delete(queryWrapper);
        //5、获取要修改的tag集合，封装为ArticleCustomTag，添加进t_article_custom_tag表
        List<String> updateTagsName = articleDto.getTagsName();
        if (!updateTagsName.isEmpty()){
            for (String tagName : updateTagsName){
                ArticleCustomTag articleTag = new ArticleCustomTag();
                articleTag.setArticleId(articleDto.getId());
                articleTag.setCustomTagName(tagName);
                articleCustomTagMapper.insert(articleTag);
            }
        }
        //6、根据 article_id 删除 该文章原有的 coverImg 集合
        LambdaQueryWrapper<ArticleCoverImg> coverImgQueryWrapper = new LambdaQueryWrapper<>();
        coverImgQueryWrapper.eq(ArticleCoverImg::getArticleId, articleDto.getId());
        articleCoverImgMapper.delete(coverImgQueryWrapper);
        //7、重新添加 coverImg 集合
        List<String> articleCoverImgUrls = articleDto.getArticleCoverImgUrls();
        for (String articleCoverImgUrl : articleCoverImgUrls){
            ArticleCoverImg articleCoverImg = new ArticleCoverImg();
            articleCoverImg.setArticleId(articleDto.getId());
            articleCoverImg.setCoverImg(articleCoverImgUrl);
            articleCoverImgMapper.insert(articleCoverImg);
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("articleId", articleDto.getId().toString());
        return map;
    }


    @Autowired
    private CommentsMapper commentsMapper;


    @Override
    public int deleteArticleToUser(Long articleId) {
        //删除文章：逻辑删除直接设置 t_article 的 is_delete 字段为 1
        return articleMapper.deleteArticleById(articleId);
    }


    @Autowired
    private UserArticleMapper userArticleMapper;

    @Override
    public int saveArticleToUser(Long articleId, Integer userId) {
        /*
         * 先查询是否已经收藏：
         *      未收藏：
         *          1、根据 articleId 在 t_article 表中增加 save_counts 字段值
         *          2、根据 userId、articleId 在 t_user_article 表中添加相关数据
         */
        UserArticle userArticle = new UserArticle();
        userArticle.setArticleId(articleId);
        userArticle.setUserId(userId.longValue());

        //先查询是否已经收藏
        LambdaQueryWrapper<UserArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserArticle::getUserId, userId);
        queryWrapper.eq(UserArticle::getArticleId, articleId);
        List<UserArticle> existUserArticles = userArticleMapper.selectList(queryWrapper);
        //若为空，说明用户未收藏该文章
        if (existUserArticles.isEmpty()){
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId, articleId);
            updateWrapper.setSql("save_counts = save_counts + 1");
            articleMapper.update(articleMapper.selectById(articleId), updateWrapper);

            return userArticleMapper.insert(userArticle);
        }else {     //不为空，说明用户已经收藏了该文章
            return -1;
        }

    }

    @Override
    public int disSaveArticleToUser(Long articleId, Integer userId) {
        /**
         * 先查询是否已经收藏：
         *      已收藏：
         *          1、根据 articleId 在 t_article 表中减小 save_counts 字段值
         *          2、根据 userId、articleId 在 t_user_article 表中删除相关数据
         */
        UserArticle userArticle = new UserArticle();
        userArticle.setUserId(userId.longValue());
        userArticle.setArticleId(articleId);

        //1、查找收藏的文章
        LambdaQueryWrapper<UserArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserArticle::getUserId, userId);
        queryWrapper.eq(UserArticle::getArticleId, articleId);
        UserArticle existUserArticle = userArticleMapper.selectOne(queryWrapper);
        //若不为空，说明用户收藏了该文章，可以进行取消文章的操作
        if (existUserArticle != null){
            LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Article::getId, articleId);
            updateWrapper.setSql("save_counts = save_counts - 1");
            articleMapper.update(articleMapper.selectById(articleId), updateWrapper);

            return userArticleMapper.deleteById(existUserArticle);
        }else {     //若为空，说明用户没收藏该文章
            return -1;
        }
    }


    @Autowired
    private UserSupportMapper userSupportMapper;

    @Override
    public int supportArticleOrComment(Integer userId, Long articleId, Long commentId) {
        /**
         * 判断是为文章点赞还为评论点赞，
         *      若是文章，先根据userId、articleId查询 t_user_support表中 是否已经有数据：
         *          若有数据，则不允许点赞，
         *          若没有数据：
         *              1、则增加 articleId 对应的文章的 support_counts字段值。
         *              2、构建 UserSupport 对象，添加进 t_user_support表。
         *    反之亦然
         */
        LambdaQueryWrapper<UserSupport> queryWrapper = new LambdaQueryWrapper<>();
        if (articleId == null){     //说明点赞的是评论
            //根据userId、commentId查询 t_user_support表，有数据不允许点赞，没数据允许点赞
            queryWrapper.eq(UserSupport::getCommentId,commentId);
            queryWrapper.eq(UserSupport::getUserId,userId);
            List<UserSupport> userSupports = userSupportMapper.selectList(queryWrapper);
            if (userSupports.isEmpty()){
                //允许点赞：获取之前的点赞数量，加1
                UserSupport userSupport = new UserSupport();
                userSupport.setUserId(userId.longValue());
                userSupport.setArticleId(articleId);
                userSupport.setCommentId(commentId);
                userSupportMapper.insert(userSupport);

                int beforeSupportCounts = commentsMapper.selectSupportCountsByCommentId(commentId);
                return commentsMapper.increaseSupportCounts(commentId, beforeSupportCounts);
            }
            return -1;
        }else {                     //说明点赞的是文章
            //根据userId、articleId查询 t_user_support表，有数据不允许点赞，没数据允许点赞
            queryWrapper.eq(UserSupport::getArticleId,articleId);
            queryWrapper.eq(UserSupport::getUserId,userId);
            List<UserSupport> userSupports = userSupportMapper.selectList(queryWrapper);
            if (userSupports.isEmpty()) {
                //允许点赞：获取之前的点赞数量，加1
                UserSupport userSupport = new UserSupport();
                userSupport.setUserId(userId.longValue());
                userSupport.setArticleId(articleId);
                userSupport.setCommentId(commentId);
                userSupportMapper.insert(userSupport);
                int beforeSupportCounts = articleMapper.selectSupportCountsByArticleId(articleId);
                return articleMapper.increaseSupportCounts(articleId,beforeSupportCounts);
            }
            return -1;
        }
    }

    @Override
    public int disSupportArticleOrComment(Integer userId, Long articleId, Long commentId) {
        /**
         * 删除 t_user_support表 userId、articleId、commentId 对应的数据
         * 判断是取消点赞文章还为取消点赞评论：
         *      去 t_user_support表 中查询是否有相关数据，有做以下步骤：
         *          若是文章，则删除 t_user_support表中相关数据，并减小 articleId 对应的文章的 support_counts字段值，
         *          若是评论，亦然
         *
         */
        LambdaQueryWrapper<UserSupport> queryWrapper = new LambdaQueryWrapper<>();

        if (articleId == null){     //说明取消点赞的是评论
            //根据userId、commentId查询 t_user_support表，有数据允许取消点赞，没数据不允许取消点赞
            queryWrapper.eq(UserSupport::getCommentId,commentId);
            queryWrapper.eq(UserSupport::getUserId,userId);
            List<UserSupport> userSupports = userSupportMapper.selectList(queryWrapper);
            if (!userSupports.isEmpty()){
                //允许取消点赞：获取之前的点赞数量，减1
                queryWrapper.eq(UserSupport::getUserId,userId);
                queryWrapper.eq(UserSupport::getCommentId,commentId);
                userSupportMapper.delete(queryWrapper);

                int beforeSupportCounts = commentsMapper.selectSupportCountsByCommentId(commentId);
                return commentsMapper.decreaseSupportCounts(commentId,beforeSupportCounts);
            }
            //不允许取消点赞
            return -1;
        }else {                     //说明取消点赞的是文章
            //根据userId、articleId查询 t_user_support表，有数据允许取消点赞，没数据不允许取消点赞
            queryWrapper.eq(UserSupport::getArticleId,articleId);
            queryWrapper.eq(UserSupport::getUserId,userId);
            List<UserSupport> userSupports = userSupportMapper.selectList(queryWrapper);
            if (!userSupports.isEmpty()) {
                //允许取消点赞：获取之前的点赞数量，减1
                queryWrapper.eq(UserSupport::getUserId,userId);
                queryWrapper.eq(UserSupport::getArticleId,articleId);
                userSupportMapper.delete(queryWrapper);

                int beforeSupportCounts = articleMapper.selectSupportCountsByArticleId(articleId);
                return articleMapper.decreaseSupportCounts(articleId,beforeSupportCounts);
            }
            return -1;
        }
    }

    @Autowired
    private UserCarerMapper userCarerMapper;

    @Override
    public int careAuthorByArticleId(Integer authorId, Integer userId) {
        /**
         * 1、构建UserCarer对象，添加进数据库表 t_user_carer
         * 2、在表 t_user 中增加 authorId 对应的userId 的 carer_counts 字段值（暂且不增加carer_counts字段）
         */
        UserCarer userCarer = new UserCarer();
        userCarer.setUserId(userId.longValue());
        userCarer.setCarerId(authorId.longValue());
        return userCarerMapper.insert(userCarer);
    }

    @Override
    public int disCareAuthorByAuthorId(Integer authorId, Integer userId) {
        /**
         * 1、删除数据库表 t_user_carer 对应的数据
         * 2、在表 t_user 中减小 authorId 对应的userId 的 carer_counts 字段值（暂且不增加carer_counts字段）
         */
        LambdaQueryWrapper<UserCarer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserCarer::getUserId,userId);
        queryWrapper.eq(UserCarer::getCarerId,authorId);
        return userCarerMapper.delete(queryWrapper);
    }


    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    private List<ArticleVo> copyList(List<Article> articleList,boolean isCreateTime,boolean isTag,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articleList){
            articleVoList.add(copy(article,isCreateTime,isTag,isBody,isCategory));
        }
        return articleVoList;
    }
    private ArticleVo copy(Article article,boolean isCreateTime,boolean isTag,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);
        /**
         * createDate、authorAccount、authorAvatarUrl、authorName、tag、category、body、coverImg都不能被复制属性，所以要单独拿出来赋值
         */
        //authorName可以先根据article 的authorAccount 查询出 具体用户信息，从中可获取用户昵称
        List<User> userList = userMapper.getUsersByAccount(article.getAuthorAccount());
        if (userList.size() != 0){
            for (User user : userList){
                String existUserAccount = user.getUserAccount();
                //若数据库中已存在的用户账号与该文章发表的用户账号大小写完全一致的话，则就是该用户
                if (existUserAccount.equals(article.getAuthorAccount())){
                    AuthorInfoVo authorInfoVo = new AuthorInfoVo();
                    articleVo.setAuthorInfo(authorInfoVo);
                    articleVo.getAuthorInfo().setAuthorId(user.getUserId().longValue());
                    articleVo.getAuthorInfo().setAuthorName(user.getUserName());
                    articleVo.getAuthorInfo().setAuthorAccount(user.getUserAccount());
                    articleVo.getAuthorInfo().setAuthorAvatarUrl(user.getUserAvatar());
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
            String articleCreateTime = new DateTime(articleMapper.selectById(articleId).getCreateDate()).toString("yyyy-MM-dd HH:mm:ss");
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


    private final static Integer OFFICIAL_ARTICLE_SHOW_NUMBER = 3;

    /**
     * 获取 官方发表的前 OFFICIAL_ARTICLE_NUMBER 条文章
     *      官方POEAzsyxk、weight == 1、按发布时间、前 OFFICIAL_ARTICLE_NUMBER 条
     * @return
     */
    @Override
    public Result getOfficialArticles() {
        //1、获取官方（where weight == 1 and is_delete = 0 order by create_date desc limit OFFICIAL_ARTICLE_NUMBER）
        List<Article> officialArticles = articleMapper.selectOfficialArticles(OFFICIAL_ARTICLE_SHOW_NUMBER);
        //2、将 articleList 转为 articleVoList 返回
        List<ArticleVo> officialArticlesVo = copyList(officialArticles,true,true,false,true);
        if (officialArticlesVo.isEmpty()){
            return Result.fail("获取官方文章失败");
        }
        return Result.succ(200,"官方文章列表如下",officialArticlesVo);
    }


    private static final int NEW_ARTICLE_LIMIT = 8;
    private static final int HOT_ARTICLE_LIMIT = 8;
    private static final int USER_ARTICLE_SHOW_NUMBER = 5;
/**
 * 获取 USER_ARTICLE_NUMBER 条用户发表的文章（每次获取的文章都不相同）
 *      weight == 0、前 USER_ARTICLE_NUMBER 条
 * 方案三：性能太差
 * 1、对数据库表 t_article 做新增字段 is_read
 *     第一次获取 USER_ARTICLE_NUMBER 条文章数据时，对 t_article 表中所有数据做 is_read = 0操作，
 *     随机获取 is_read = 0 的5条数据，将获取到的数据修改为 is_read = 1
 *         非第一次获取 USER_ARTICLE_NUMBER 条文章数据时，随机获取 is_read = 0 的随机5条数据，将获取到的数据修改为 is_read = 1
 * 2、以此反复
 *
 * 方案五：性能最好
 * 1、获取 USER_ARTICLE_SHOW_NUMBER 条 用户文章id 集合，判断 redis库1 中 officialArticleShow 的长度 是否为0，
 *     若没有则为第一次请求，都添加进去（右边插入：rpush）
 *     若有则不是第一次请求，则写一个递归的私有方法，方法内容如下：
 *          将 用户文章id 与 redis库1 的 文章id 进行比对，List<Long> repeatIds 记录 一致的id
 *          若 repeatIds.size() != 0，
 *              则重新获取 repeatIds.size() 条 用户文章id ，递归
 *          若 repeatIds.size() == 0
 *              退出递归
 * 2、此时，redis库1 中的文章id 即为要展示的 文章，取出 id集合（左边取出：lpop）
 */
    @Override
    public Result getUserArticles() {
        List<ArticleVo> userArticlesVo = new ArrayList<>();
        //获取为被查看的文章数，若为0则说明需要重置 t_article 中的 is_read
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getIsRead, 0);
        queryWrapper.eq(Article::getWeight, 0);
        int notViewed = articleMapper.selectCount(queryWrapper).intValue();
        if (notViewed == 0){
            //重置数据库中 t_article 中的 is_read 为 0
            articleMapper.update(null, new LambdaUpdateWrapper<Article>().setSql("is_read = 0"));
        }
        //从 is_read 为 0 的数据中随机获取数据，获取到的数据设置 is_read = 1
        List<Article> articles = articleMapper.selectRandUserArticles(USER_ARTICLE_SHOW_NUMBER,ArticleUtil.VISIT_PERMISSION_MY);
        for (Article article : articles){
            article.setIsRead(1);
            articleMapper.update(article, new LambdaUpdateWrapper<Article>().eq(Article::getId, article.getId()));
        }
        userArticlesVo.addAll(copyList(articles, true, true, false, true));
        if (userArticlesVo.isEmpty()){
            return Result.fail("获取用户文章出现错误");
        }
        return Result.succ(200,"获取文章成功", userArticlesVo);
    }

    @Override
    public List<ArticleVo> getHotArticle() {
        List<Article> hotArticles = articleMapper.selectHotArticle(HOT_ARTICLE_LIMIT);
        return copyList(hotArticles, true,false,false,true);
    }

    @Override
    public List<ArticleVo> getNewArticle() {
        List<Article> newArticles = articleMapper.selectNewArticle(NEW_ARTICLE_LIMIT);
        return copyList(newArticles, true,false,false,true);
    }


    @Autowired
    private ThreadService threadService;

    /**
     * 查看完文章，不能直接增加阅读数量。
     * 因为MySQL在做更新操作时是加写锁的，会阻塞其他的操作，性能较低，
     * 但我们希望 文章详情的展示操作 不受 阅读数量 的更新而影响，即使更新出了问题也不能影响查看文章的操作
     * 所以需要线程池（不同线程）来操作
     * @param articleId 文章id
     * @return
     */
    @Override
    public ArticleVo findArticleById(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true,true,true,true);
        threadService.updateArticleViewCount(articleMapper, article);
        return articleVo;
    }


    @Override
    public void increaseCommentCountsByArticleId(Long articleId) {
        articleMapper.increaseCommentCountsByArticleId(articleId);
    }

}
