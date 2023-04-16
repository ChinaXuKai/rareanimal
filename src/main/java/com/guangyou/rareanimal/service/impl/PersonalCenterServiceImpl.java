package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guangyou.rareanimal.mapper.*;
import com.guangyou.rareanimal.pojo.*;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.*;
import com.guangyou.rareanimal.service.PersonalCenterService;
import com.guangyou.rareanimal.utils.ArticleUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xukai
 * @create 2023-01-21 22:40
 */
@Service
public class PersonalCenterServiceImpl implements PersonalCenterService {

    @Autowired
    private PersonalCenterMapper personalCenterMapper;


    @Override
    public int getMyFansCounts(Integer authorId) {
        return personalCenterMapper.selectMyFansCounts(authorId.longValue());
    }


    @Autowired
    private UserCarerMapper userCarerMapper;

    @Override
    public UserCarerVo getMyCarers(PageDto pageDto, Integer userId) {
        PageDataVo<CaredBloggerVo> pageUserCarerData = new PageDataVo<>();
        UserCarerVo userCarerVo = new UserCarerVo();
        List<CaredBloggerVo> caredBloggerList = new ArrayList<>();

        //获取用户关注的博主id集合
        List<Long> myCarersId = userCarerMapper.selectCarersIdPageByUserId(userId.longValue(),pageDto.getPageSize() * (pageDto.getPage() - 1), pageDto.getPageSize());
        //循环博主id 获取博主昵称和博主账号
        for (Long carerId : myCarersId){
            User user = personalCenterMapper.selectCarerByUserId(carerId);
            CaredBloggerVo caredBlogger = new CaredBloggerVo();
            caredBlogger.setBloggerName(user.getUserName());
            caredBlogger.setBloggerAvatar(user.getUserAvatar());
            caredBlogger.setBloggerUserId(carerId);
            caredBloggerList.add(caredBlogger);
        }

        //设置 数据库中关注博主总数（total）、每页显示数量（size）、当前第几页（current）、总共有多少页数据（pages）
        LambdaQueryWrapper<UserCarer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserCarer::getUserId, userId);
        int total = userCarerMapper.selectCount(queryWrapper).intValue();
        pageUserCarerData.setTotal(total);
        int isRemainZero = total%pageDto.getPageSize();
        if (isRemainZero != 0){
            pageUserCarerData.setPages( (total/pageDto.getPageSize()) + 1);
        }else {
            pageUserCarerData.setPages( total/pageDto.getPageSize() );
        }
        pageUserCarerData.setSize(pageDto.getPageSize());
        pageUserCarerData.setCurrent(pageDto.getPage());
        pageUserCarerData.setPageData(caredBloggerList);
        userCarerVo.setUserId(userId.longValue());
        userCarerVo.setPageBloggerData(pageUserCarerData);
        return userCarerVo;
    }


    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private ArticleUtil articleUtil;
    @Override
    public PageDataVo<ArticleVo> getMyArticles(PageDto pageDto, String userAccount, Integer userId) {
        PageDataVo<ArticleVo> pageDataVo = new PageDataVo<>();
        List<Article> articleList = personalCenterMapper.selectMyArticles(userAccount, pageDto.getPageSize() * (pageDto.getPage() - 1), pageDto.getPageSize());
        List<ArticleVo> articleVoList = articleUtil.copyList(userId,articleList, true, true, false, true);
        pageDataVo.setPageData(articleVoList);
        //设置 数据库中用户文章总数（total）、每页显示数量（size）、当前第几页（current）、总共有多少页数据（pages）
        pageDataVo.setCurrent(pageDto.getPage());
        pageDataVo.setSize(pageDto.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getAuthorAccount, userAccount);
        queryWrapper.eq(Article::getIsDelete, 0);
        int total = articleMapper.selectCount(queryWrapper).intValue();
        pageDataVo.setTotal(total);
        int isRemainZero = total%pageDto.getPageSize();
        if (isRemainZero != 0){
            pageDataVo.setPages( (total/pageDto.getPageSize()) + 1);
        }else {
            pageDataVo.setPages( total/pageDto.getPageSize() );
        }
        return pageDataVo;
    }

    @Autowired
    private UserArticleMapper userArticleMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ArticleCoverImgMapper articleCoverImgMapper;

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

        int total = userArticleMapper.selectSaveArticleCount(userId);
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

}
