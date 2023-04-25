package com.guangyou.rareanimal.utils;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guangyou.rareanimal.mapper.*;
import com.guangyou.rareanimal.pojo.*;
import com.guangyou.rareanimal.pojo.vo.*;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xukai
 * @create 2023-04-06 13:18
 */
@Repository
public class CopyUtils {

//    /**
//     * 赋值 userList 为 userVoList
//     * @param users 用户集合
//     * @return 用户vo集合
//     */
//    public List<UserVo> userListCopy(List<User> users){
//        List<UserVo> userVos = new ArrayList<>();
//        for (User user : users){
//            userVos.add(userCopy(user));
//        }
//        return userVos;
//    }
//    /**
//     * 赋值单个 user 为 userVo
//     * @param user  用户
//     * @return 用户vo对象
//     */
//    private UserVo userCopy(User user) {
//        UserVo userVo = new UserVo();
//        //UserVo 的userId、userName、userAccount 可以使用BeanUtils
//        BeanUtils.copyProperties(user,userVo);
//        //userPermission、createTime、updateTime需要手动赋值
//        if (user.OFFICIAL_ACCOUNT.equals(user.getUserAccount())){
//            userVo.setUserPermission("admin");
//        }
//        //userPermission先使用默认值，之后再写权限
//        String createTime = new DateTime(user.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss");
//        userVo.setCreateTime(createTime);
////        userVo.setUpdateTime(new DateTime(System.currentTimeMillis()).toString("yyyy-MM-dd HH:mm:ss"));
//        return userVo;
//    }

//    @Autowired
//    private AnimalIntroduceMapper animalIntroduceMapper;
//    /**
//     * 赋值 animalList 为 animalVoList
//     * @param animalList 动物集合
//     * @return 动物vo集合
//     */
//    public List<AnimalVo> animalListCopy(List<Animal> animalList) {
//        List<AnimalVo> animalVos = new ArrayList<>();
//        for (Animal animal : animalList){
//            animalVos.add(animalCopy(animal));
//        }
//        return animalVos;
//    }
//    /**
//     * 赋值单个 animal 为 animalVo
//     * @param animal 动物
//     * @return 动物vo
//     */
//    private AnimalVo animalCopy(Animal animal) {
//        AnimalVo animalVo = new AnimalVo();
//        BeanUtils.copyProperties(animal, animalVo);
//        //还有 animalIntroduce、animalLabel、animalId需要手动赋值
//        //animalId
//        animalVo.setAnimalId(animal.getAnimalId().longValue());
//        //animalIntroduce
//        LambdaQueryWrapper<AnimalIntroduce> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(AnimalIntroduce::getAnimalId,animal.getAnimalId());
//        AnimalIntroduce animalIntroduce = animalIntroduceMapper.selectOne(queryWrapper);
//        AnimalIntroduceVo animalIntroduceVo = animalIntroduceCopy(animalIntroduce);
//        animalVo.setAnimalIntroduce(animalIntroduceVo);
//        //animalLabel
//        getAnimalLabel(animalVo);
//        return animalVo;
//    }


//    public List<AnimalIntroduceVo> animalIntroduceListCopy(List<AnimalIntroduce> AnimalIntroduceList) {
//        List<AnimalIntroduceVo> animalIntroduceVos = new ArrayList<>();
//        for (AnimalIntroduce animalIntroduce : AnimalIntroduceList){
//            animalIntroduceVos.add(animalIntroduceCopy(animalIntroduce));
//        }
//        return animalIntroduceVos;
//    }
//    private AnimalIntroduceVo animalIntroduceCopy(AnimalIntroduce animalIntroduce) {
//        AnimalIntroduceVo animalIntroduceVo = new AnimalIntroduceVo();
//        BeanUtils.copyProperties(animalIntroduce, animalIntroduceVo);
//        return animalIntroduceVo;
//    }
//
//
//    @Autowired
//    private AnimalLabelMapper animalLabelMapper;
//    /**
//     * 将数据库里的 每个动物的 动物标签 拆分成 List<String>类型的多个动物标签，并赋值给对应动物的 animalLabel
//     * @param animalList 动物集合
//     * @return
//     */
//    private void getAnimalLabels(List<AnimalVo> animalList){
//        for (AnimalVo animalVo : animalList){
//            getAnimalLabel(animalVo);
//        }
//    }
//    /**
//     * 将数据库里的 单个动物的 动物标签 拆分成 List<String>类型的多个动物标签，并赋值给动物的 animalLabel
//     * @param animalVo 单个动物
//     * @return
//     */
//    private void getAnimalLabel(AnimalVo animalVo){
//        Long animalId = animalVo.getAnimalId();
//        AnimalLabelVo animalLabelVo = new AnimalLabelVo();
//        LambdaQueryWrapper<AnimalLabel> queryWrapper = new LambdaQueryWrapper<>();
//
//        queryWrapper.eq(AnimalLabel::getAnimalId,animalId);
//        AnimalLabel animalLabel = animalLabelMapper.selectOne(queryWrapper);
//        BeanUtils.copyProperties(animalLabel, animalLabelVo);
//        String[] animalLabels = animalLabelVo.getAnimalLabel().split("、");
//
//        List<String> labelList = new ArrayList<>(Arrays.asList(animalLabels));
//        animalVo.setAnimalLabel(labelList);
//    }


    @Autowired
    private UserMapper userMapper;
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
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private UserCarerMapper userCarerMapper;

    /**
     * 赋值 articleList 为 articleVoList
     * @param articleList 文章集合
     * @param isCreateTime 是否要返回创建时间
     * @param isTag 是否要返回标签
     * @param isBody 是否要返回文章体
     * @param isCategory 是否要返回文章圈子
     * @return 文章vo集合
     */
    public List<ArticleVo> articleListCopy(List<Article> articleList, boolean isCreateTime, boolean isTag, boolean isBody, boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : articleList){
            articleVoList.add(articleCopy(article,isCreateTime,isTag,isBody,isCategory));
        }
        return articleVoList;
    }
    /**
     * 赋值 article 为 articleVo
     * @param article 文章
     * @param isCreateTime 是否要返回创建时间
     * @param isTag 是否要返回标签
     * @param isBody 是否要返回文章体
     * @param isCategory 是否要返回文章圈子
     * @return 文章vo
     */
    public ArticleVo articleCopy(Article article,boolean isCreateTime,boolean isTag,boolean isBody,boolean isCategory){
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
                //设置 ArticleVo 的属性：作者id、作者名称、作者账号、作者头像、粉丝数、关注数、社交动态数
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


    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private ActivityCustomTagMapper activityCustomTagMapper;
    @Autowired
    private ActivityJoinMapper activityJoinMapper;

    /**
     * 赋值 activityList 为 activityVoList
     * @param activityList 活动集合
     * @return 活动vo集合
     */
    public List<ActivityVo> activityListCopy(List<Activity> activityList){
        List<ActivityVo> activityVoList = new ArrayList<>();
        for (Activity activity : activityList){
            ActivityVo activityVo = activityCopy(activity);
            activityVoList.add(activityVo);
        }
        return activityVoList;
    }
    /**
     * 赋值 activity 为 activityVo
     * @param activity 活动
     * @return 活动vo
     */
    private ActivityVo activityCopy(Activity activity) {
        ActivityVo activityVo = new ActivityVo();
        BeanUtils.copyProperties(activity, activityVo);
        /*
        publisherVo、requestTime、startTime、endTime、updateTime、
        tagsDescribe、joinCount、isHot、isEnd
         */
        //publisherVo
        User publisher = userMapper.getUserById(activity.getPublishUid().intValue());
        UserVo publisherVo = new UserVo();
        BeanUtils.copyProperties(publisher, publisherVo);
        activityVo.setPublisherVo(publisherVo);
        //requestTime、startTime、endTime、updateTime
        String requestTime = new DateTime(activity.getRequestTime()).toString("yyyy-MM-dd HH:mm:ss");
        String startTime = new DateTime(activity.getStartTime()).toString("yyyy-MM-dd HH:mm:ss");
        String endTime = new DateTime(activity.getEndTime()).toString("yyyy-MM-dd HH:mm:ss");
        String updateTime = new DateTime(activity.getUpdateTime()).toString("yyyy-MM-dd HH:mm:ss");
        activityVo.setRequestTime(requestTime);
        activityVo.setStartTime(startTime);
        activityVo.setEndTime(endTime);
        activityVo.setUpdateTime(updateTime);
        //coverUrl
//        LambdaQueryWrapper<Activity> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Activity::getActivityId, activity.getActivityId());
//        String coversUrl = activityMapper.selectOne(queryWrapper).getCoverUrl();
//        activityVo.setCoversUrl(coversUrl);
        //tagsDescribe
        List<String> tags = activityCustomTagMapper.getTagById(activity.getActivityId());
        activityVo.setTagsDescribe(tags);
        //joinCount
        LambdaQueryWrapper<ActivityJoin> aJoinQueryWrapper = new LambdaQueryWrapper<>();
        aJoinQueryWrapper.eq(ActivityJoin::getActivityId, activity.getActivityId());
        int joinCount = activityJoinMapper.selectCount(aJoinQueryWrapper).intValue();
        activityVo.setJoinCount(joinCount);
        //isHot：(已报名人数大于参见人数上限一半 且 参见人数上限大于5)   或   已报名人数大于总用户的千分之一
        Long userCount = userMapper.selectCount(null);
        if ( ( (joinCount>(activity.getPeopleCeiling()/2)) && activity.getPeopleCeiling()>5 )
                ||  (joinCount > userCount/1000)){
            activityVo.setIsHot(true);
        }
        //isEnd：当前时间的时间戳 小于 截止时间的时间戳，则设置为 false(未截止)
        if (System.currentTimeMillis() < activity.getEndTime()){
            activityVo.setIsEnd(false);
        }
        return activityVo;
    }


    /**
     *
     * @param opinionList
     * @return
     */
    public List<OpinionVo> opinionListCopy(List<Opinion> opinionList) {
        List<OpinionVo> opinionVoList = new ArrayList<>();
        for (Opinion opinion : opinionList){
            OpinionVo opinionVo = opinionCopy(opinion);
            opinionVoList.add(opinionVo);
        }
        return opinionVoList;
    }
    private OpinionVo opinionCopy(Opinion opinion) {
        OpinionVo opinionVo = new OpinionVo();
        BeanUtils.copyProperties(opinion, opinionVo);
        //userVo、submitTime、updateTime
        //userVo
        User user = userMapper.getUserById(opinion.getUserId().intValue());
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        opinionVo.setUserVo(userVo);
        //submitTime、updateTime
        String submitTime = new DateTime(opinion.getSubmitTime()).toString("yyyy-MM-dd HH:mm:ss");
        String updateTime = new DateTime(opinion.getUpdateTime()).toString("yyyy-MM-dd HH:mm:ss");
        opinionVo.setSubmitTime(submitTime);
        opinionVo.setUpdateTime(updateTime);
        return opinionVo;
    }


    @Autowired
    private QuestionTagMapper questionTagMapper;
    @Autowired
    private AnswerQuestionMapper answerQuestionMapper;

    public List<QuestionVo> questionListCopy(Integer userId,List<Question> questionList) {
        List<QuestionVo> questionVoList = new ArrayList<>();
        for (Question question : questionList){
            QuestionVo questionVo = questionCopy(userId,question);
            questionVoList.add(questionVo);
        }
        return questionVoList;
    }
    private QuestionVo questionCopy(Integer userId,Question question) {
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(question, questionVo);
        Long questionId = question.getQuestionId();
        Integer publisherId = question.getUserId();
        //authorInfo、questionTags、publishTime、updateTime、answers
        //authorInfo
        AuthorInfoVo authorInfo = new AuthorInfoVo();
        authorInfo.setAuthorId(publisherId.longValue());
        User user = userMapper.selectById(publisherId);
        authorInfo.setAuthorName(user.getUserName());
        authorInfo.setAuthorAccount(user.getUserAccount());
        authorInfo.setAuthorAvatarUrl(user.getUserAvatar());
        authorInfo.setCreateTime(new DateTime(user.getCreateTime()).toString("yyyy-MM-dd"));
        authorInfo.setFansCount(userCarerMapper.selectCount(new LambdaQueryWrapper<UserCarer>().eq(UserCarer::getCarerId, userId)).intValue());
        //authorInfo:isCared
        if (userId == null){
            authorInfo.setIsCared(0);
        }else {
            LambdaQueryWrapper<UserCarer> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserCarer::getUserId, userId);
            queryWrapper.eq(UserCarer::getCarerId, publisherId);
            authorInfo.setIsCared(userCarerMapper.selectCount(queryWrapper).intValue());
        }
        //questionTags：根据问题id 在 t_question_tag表 中查找集合
        questionVo.setQuestionTags(questionTagMapper.selectTagsById(questionId));
        //publishTime、updateTime
        questionVo.setPublishTime(new DateTime(question.getPublishTime()).toString("yyyy-MM-dd HH:mm:ss"));
        questionVo.setUpdateTime(new DateTime(question.getUpdateTime()).toString("yyyy-MM-dd HH:mm:ss"));
        //answers：根据 问题id 在 t_answer_question表 中查找集合
        List<AnswerQuestionVo> answerVoList = answerQuestionListCopy(answerQuestionMapper.selectList(
                        new LambdaQueryWrapper<AnswerQuestion>().
                                eq(AnswerQuestion::getQuestionId, questionId)));
        questionVo.setAnswers(answerVoList);
        return questionVo;
    }


    public List<AnswerQuestionVo> answerQuestionListCopy(List<AnswerQuestion> answerList) {
        List<AnswerQuestionVo> answerVoList = new ArrayList<>();
        for (AnswerQuestion answer : answerList){
            AnswerQuestionVo answerVo = answerQuestionCopy(answer);
            answerVoList.add(answerVo);
        }
        return answerVoList;
    }
    private AnswerQuestionVo answerQuestionCopy(AnswerQuestion answer) {
        AnswerQuestionVo answerVo = new AnswerQuestionVo();
        BeanUtils.copyProperties(answer, answerVo);
        //author、isPerfectAnswer
        //isPerfectAnswer
        if (answer.getIsPerfectAnswer() == 1){
            answerVo.setIsPerfectAnswer(true);
        }else {
            answerVo.setIsPerfectAnswer(false);
        }
        //author
        Long authorId = answer.getUserId();
        User user = userMapper.getUsersByUid(authorId);
        AuthorVo authorVo = new AuthorVo();
        authorVo.setUserId(authorId.intValue());
        authorVo.setUserName(user.getUserName());
        authorVo.setUserAccount(user.getUserAccount());
        authorVo.setUserAvatar(user.getUserAvatar());
        answerVo.setAuthor(authorVo);
        return answerVo;
    }


}
