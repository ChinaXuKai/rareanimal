package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.vo.CategoryVo;
import com.guangyou.rareanimal.pojo.vo.RecommendArticleVo;
import com.guangyou.rareanimal.pojo.vo.RecommendCategoryVo;
import com.guangyou.rareanimal.pojo.vo.RecommendUserVo;
import com.guangyou.rareanimal.service.RecommendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xukai
 * @create 2023-02-10 11:00
 */
@Slf4j
@RestController
@RequestMapping("/recommend")
@Api(tags = "推荐系统控制器")
public class RecommendController {

    private final static Integer RECOMMEND_ARTICLE_CATEGORY_NUMBER = 5;
    private final static Integer RECOMMEND_USER_NUMBER = 3;
    private final static Integer RECOMMEND_ARTICLE_NUMBER = 3;

    @Autowired
    private RecommendService recommendService;

    @ApiOperation(value = "推荐文章圈子",notes = "推荐文章圈子，每天刷新")
    @GetMapping("/articleCategory")
    public Result articleCategory(){
        List<RecommendCategoryVo> recommendCategories = recommendService.getRecommendArticleCategory(RECOMMEND_ARTICLE_CATEGORY_NUMBER);
        if (recommendCategories.isEmpty()){
            return Result.fail("推荐异常");
        }
        return Result.succ(200,"推荐圈子",recommendCategories);
    }


    @ApiOperation(value = "推荐博主用户",notes = "推荐博主用户，每小时刷新")
    @GetMapping("/user")
    public Result user(){
        List<RecommendUserVo> recommendUsers = recommendService.getRecommendUser(RECOMMEND_USER_NUMBER);
        if (recommendUsers.isEmpty()){
            return Result.fail("推荐异常");
        }
        return Result.succ(200, "推荐博主", recommendUsers);
    }


    @ApiOperation(value = "推荐文章",notes = "推荐文章，每分钟刷新")
    @GetMapping("/article")
    public Result article(){
        List<RecommendArticleVo> recommendArticleVos = recommendService.getRecommendArticle(RECOMMEND_ARTICLE_NUMBER);
        if (recommendArticleVos.isEmpty()){
            return Result.fail("推荐异常");
        }
        return Result.succ(200, "推荐文章",  recommendArticleVos);
    }

}
