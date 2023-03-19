package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guangyou.rareanimal.mapper.*;
import com.guangyou.rareanimal.pojo.*;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.*;
import com.guangyou.rareanimal.service.PersonalCenterService;
import org.joda.time.DateTime;
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
    @Override
    public PageDataVo<ArticleVo> getMyArticles(PageDto pageDto,String userAccount) {
        PageDataVo<ArticleVo> pageDataVo = new PageDataVo<>();
        List<Article> articleList = personalCenterMapper.selectMyArticles(userAccount, pageDto.getPageSize() * (pageDto.getPage() - 1), pageDto.getPageSize());
        List<ArticleVo> articleVoList = copyList(articleList, true, true, false, true);
        pageDataVo.setPageData(articleVoList);
        //设置 数据库中用户文章总数（total）、每页显示数量（size）、当前第几页（current）、总共有多少页数据（pages）
        pageDataVo.setCurrent(pageDto.getPage());
        pageDataVo.setSize(pageDto.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getAuthorAccount, userAccount);
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
    private UserMapper userMapper;
    @Autowired
    private CustomTagMapper customTagMapper;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleCoverImgMapper articleCoverImgMapper;

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
         * coverImg、createDate、authorName、tag、category、body都不能被复制属性，所以要单独拿出来赋值
         */
        //authorName可以先根据article 的authorAccount 查询出 具体用户信息，从中可获取用户昵称
        List<User> userList = userMapper.getUsersByAccount(article.getAuthorAccount());
        if (userList.size() != 0){
            for (User user : userList){
                String existUserAccount = user.getUserAccount();
                //若数据库中已存在的用户账号与该文章发表的用户账号大小写完全一致的话，则就是该用户
                if (existUserAccount.equals(article.getAuthorAccount())){
                    articleVo.setAuthorName(user.getUserName());
                }
            }
        }
        //coverImg：可以先从
        Long articleId = article.getId();
        List<String> coverImgs = articleCoverImgMapper.selectCoverImgByArticleId(articleId);
        articleVo.setCoverImg(coverImgs);
        //若需要从数据库查询时间则从数据库中查询，否则设置为当前时间
        if (isCreateTime){
            String articleCreateTime = new DateTime(articleMapper.selectById(articleId).getCreateDate()).toString("yyyy-MM-dd HH:mm:ss");
            articleVo.setCreateDate(articleCreateTime);
        }else {
            articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        }
        //不是所有的接口 都需要 tag
        if (isTag){     //若需要 tag 属性
            articleVo.setTags(customTagMapper.selectTagsByArticleId(articleId));
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
