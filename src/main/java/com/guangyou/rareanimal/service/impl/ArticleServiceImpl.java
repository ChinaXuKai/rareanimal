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
        article.setWeight(0);
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
        //要删除文章就要 根据 articleId 删除对应t_article、t_article_body、t_article_tag、t_comment中的数据
        articleMapper.delete(new LambdaQueryWrapper<Article>().eq(Article::getId, articleId));
        articleBodyMapper.delete(new LambdaQueryWrapper<ArticleBody>().eq(ArticleBody::getArticleId,articleId));
        articleCustomTagMapper.delete(new LambdaQueryWrapper<ArticleCustomTag>().eq(ArticleCustomTag::getArticleId, articleId));
        return commentsMapper.delete(new LambdaQueryWrapper<Comment>().eq(Comment::getArticleId, articleId));
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


    @Override
    public PageDataVo<ArticleVo> listSaveArticles(PageDto pageDto, Integer userId) {
        /*
         * 1、在t_user_article表中按照 user_id 查询 article_id 集合
         * 2、根据 article_id 集合在 t_article表中查询如下字段信息：
         *      id、author_name、title、comment_counts、view_counts、support_counts、
         *      save_counts
         * 3、设置 数据库中收藏文章总数（total）、每页显示数量（size）、当前第几页（current）、总共有多少页数据（pages）
         */
        PageDataVo<ArticleVo> pageDataVo = new PageDataVo<>();
        ArrayList<ArticleVo> saveArticlesVo = new ArrayList<>();
        List<Article> saveArticles = articleMapper.selectSaveArticles(userId, pageDto.getPageSize() * (pageDto.getPage() - 1), pageDto.getPageSize());
        for (Article saveArticle : saveArticles){
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(saveArticle, articleVo);
            //author_name需要通过 根据author_account 查询t_user表 获取作者
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getUserAccount, saveArticle.getAuthorAccount());
            User author = userMapper.selectOne(queryWrapper);
            //获取到作者后赋值作者昵称并添加进 saveArticlesVo集合
            AuthorInfoVo authorInfoVo = new AuthorInfoVo();
            articleVo.setAuthorInfo(authorInfoVo);
            articleVo.getAuthorInfo().setAuthorId(author.getUserId().longValue());
            articleVo.getAuthorInfo().setAuthorName(author.getUserName());
            articleVo.getAuthorInfo().setAuthorAccount(author.getUserAccount());
            articleVo.getAuthorInfo().setAuthorAvatarUrl(author.getUserAvatar());
            //根据文章id 获取文章封面url地址集合
            List<String> coverImgList = articleCoverImgMapper.selectCoverImgByArticleId(saveArticle.getId());
            articleVo.setCoverImg(coverImgList);
            saveArticlesVo.add(articleVo);
        }

        LambdaQueryWrapper<UserArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserArticle::getUserId, userId);
        int total = userArticleMapper.selectCount(queryWrapper).intValue();
        pageDataVo.setTotal(total);
        pageDataVo.setPageData(saveArticlesVo);
        pageDataVo.setSize(pageDto.getPageSize());
        pageDataVo.setCurrent(pageDto.getPage());
        int isRemainZero = total%pageDto.getPageSize();
        if (isRemainZero != 0){
            pageDataVo.setPages( (total/pageDto.getPageSize()) + 1);
        }else {
            pageDataVo.setPages( total/pageDto.getPageSize() );
        }
        return pageDataVo;
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



    private final static Integer RAND_ARTICLE_NUMBER = 3;
    /**
     * 获取 articleList：
     * 1、优先获取官方POEAzsyxk（weight == 1）
     * 2、再获取 3 条文章（）
     * 3、将 步骤1 和 步骤2获取 到的数据添加到 articleList中
     */
    @Override
    public Result listArticle() {
        List<ArticleVo> resultArticleVoList = new ArrayList<>();
        //1、优先获取官方POEAzsyxk（weight == 1）
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getWeight,1);
        List<Article> articleList = articleMapper.selectList(queryWrapper);
        //将 articleList 转为 articleVoList 返回
        List<ArticleVo> articleVoList = copyList(articleList,true,true,false,false);
        //2、再获取 RAND_ARTICLE_NUMBER 条随机数据
        List<Article> randArticles = articleMapper.selectRandArticles(RAND_ARTICLE_NUMBER);
        //将 randArticles 转为 randArticleVoList 返回
        List<ArticleVo> randArticleVoList = copyList(randArticles, true, true, false, false);
        //3、将 articleVoList 和 randArticleVoList 获取到的数据添加到 resultArticleVoList中
        resultArticleVoList.addAll(articleVoList);
        resultArticleVoList.addAll(randArticleVoList);
        if (resultArticleVoList.isEmpty()){
            return Result.fail("获取文章出现错误");
        }
        return Result.succ(200,"获取文章成功", resultArticleVoList);
    }


    @Override
    public List<ArticleVo> getHotArticle(int hotArticleLimit) {
        //select id,title from t_article order by view_counts desc
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.select(Article::getId,Article::getTitle,Article::getAuthorAccount);
        queryWrapper.last("limit " + hotArticleLimit);
        List<Article> hotArticles = articleMapper.selectList(queryWrapper);
        return copyList(hotArticles, true,false,false,false);
    }


    @Override
    public List<ArticleVo> getNewArticle(int newArticleLimit) {
        //select id,title from t_article order by create_date desc
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.select(Article::getId,Article::getTitle,Article::getAuthorAccount);
        queryWrapper.last("limit " + newArticleLimit);
        List<Article> newArticles = articleMapper.selectList(queryWrapper);
        return copyList(newArticles, true,false,false,false);
    }


    @Autowired
    private ThreadService threadService;


    @Override
    public ArticleVo findArticleById(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        ArticleVo articleVo = copy(article, true,true,true,true);
        /**
         * 查看完文章，不能直接增加阅读数量。
         * 因为MySQL在做更新操作时是加写锁的，会阻塞其他的操作，性能较低，
         * 但我们希望 文章详情的展示操作 不受 阅读数量 的更新而影响，即使更新出了问题也不能影响查看文章的操作
         * 所以需要线程池（不同线程）来操作
         */
        threadService.updateArticleViewCount(articleMapper, article);
        return articleVo;
    }


    @Override
    public void increaseCommentCountsByArticleId(Long articleId) {
        articleMapper.increaseCommentCountsByArticleId(articleId);
    }

}
