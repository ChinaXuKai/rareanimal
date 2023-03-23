package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.mapper.ArticleMapper;
import com.guangyou.rareanimal.pojo.CategoryTheme;
import com.guangyou.rareanimal.pojo.vo.ArticleCategoriesVo;
import com.guangyou.rareanimal.pojo.vo.ArticleVo;
import com.guangyou.rareanimal.pojo.vo.CustomTagVo;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.service.ArticleService;
import com.guangyou.rareanimal.service.CategoryService;
import com.guangyou.rareanimal.service.CustomTagService;
import com.guangyou.rareanimal.utils.ArticleUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xukai
 * @create 2022-12-28 9:59
 */
@Slf4j
@RestController
@RequestMapping("/article")
@Api(tags = "文章相关接口-未登录可看（都不需要传jwt）")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CustomTagService customTagService;
    @Autowired
    private CategoryService categoryService;

    private static final int TAG_LIMIT = 5;

    @ApiOperation(value = "官方文章集合显示",notes = "返回官方发布的文章")
    @GetMapping("getOfficialArticles")
    public Result getOfficialArticles(){
        return articleService.getOfficialArticles();
    }

    @ApiOperation(value = "用户文章集合显示",notes = "返回用户发布的文章")
    @GetMapping("getUserArticles")
    public Result getUserArticles(){
        return articleService.getUserArticles();
    }


    @ApiOperation(value = "最热标签/推荐标签",notes = "可以获取最热标签/发表文章时可推荐最热标签")
    @GetMapping("getHotTag")
    public Result getHotTag(){
        //1、标签所拥有的文章数量最多的就是最热标签
        //2、查询 根据 custom_tag_name 分组计数，从小到大排列 取前 LIMIT 个最热标签的id

        List<CustomTagVo> hotTags = customTagService.getHotTagsInfo(TAG_LIMIT);
        if (hotTags.size() != 0){
            return Result.succ(200,"以下标签为当前使用最多标签",hotTags);
        }else {
            return Result.succ(200, "当前还没有最热标签", null);
        }
    }


    @ApiOperation(value = "查询非官方发布的最热文章",notes = "可以获取非官方发布的最热文章")
    @GetMapping("getHotArticle")
    public Result getHotArticle(){
        List<ArticleVo> hotArticles = articleService.getHotArticle();
        if (hotArticles.size() == 0){
            return Result.succ(200, "当前还没有最热文章", null);
        }else {
            return Result.succ(200, "已查询出最热文章",hotArticles);
        }
    }


    @ApiOperation(value = "查询非官方发布的最新文章",notes = "可以获取非官方发布的最新文章")
    @GetMapping("getNewArticle")
    public Result getNewArticle(){
        List<ArticleVo> newArticles = articleService.getNewArticle();
        if (newArticles.size() == 0){
            return Result.succ(200, "当前还没有最热文章", null);
        }else {
            return Result.succ(200, "已查询出最热文章",newArticles);
        }
    }

    @Autowired
    private ArticleMapper articleMapper;

    @ApiOperation(value = "文章详情",notes = "可以查看文章的具体信息")
    @GetMapping("viewArticle/{id}")
    public Result viewArticleById(@PathVariable("id") Long articleId){
        //若文章id错误（数据不存在），则 不允许访问
        if (articleMapper.selectById(articleId) == null){
            return Result.fail(Result.FORBIDDEN,"文章id错误，数据不存在",null);
        }
        //若文章已被逻辑删除，则 不允许访问
        if (articleMapper.selectById(articleId).getIsDelete() == 1){
            return Result.fail(Result.FORBIDDEN,"该文章已被逻辑删除，不可访问",null);
        }

        //获取文章访问权限，若不是 “全部可见” ，则不允许访问
        String visitPermission = articleMapper.selectVisitPermission(articleId);
        if (!ArticleUtil.VISIT_PERMISSION_ALL.equals(visitPermission)){
            return Result.fail(Result.FORBIDDEN,"该博主设置了文章访问权限，你当前还没权限访问哦",null);
        }
        ArticleVo articleVo = articleService.findArticleById(articleId);
        if (articleVo == null){
            return Result.fail("未查询到该文章");
        }else {
            return Result.succ(200, "已查询到该文章", articleVo);
        }
    }

    @ApiOperation(value = "获取文章圈子的主题集合",notes = "获取文章圈子的主题集合")
    @GetMapping("/getCategoryTheme")
    public Result getCategoryTheme(){
        List<CategoryTheme> categoryThemes = categoryService.getAllTheme();
        return Result.succ(200,"",categoryThemes);
    }


    @ApiOperation(value = "获取主题id对应的文章圈子集合",notes = "获取主题id对应的文章圈子集合")
    @GetMapping("/getArticleCategory/{themeId}")
    public Result getAllArticleCategory(@PathVariable("themeId") Long themeId){
        ArticleCategoriesVo articleCategories = categoryService.getCategoryByThemeId(themeId);
        return Result.succ(200, "请在下列文章圈子中选择一样", articleCategories);
    }

    @ApiOperation(value = "文章分类圈子",notes = "获取所有的文章圈子")
    @GetMapping("getAllArticleCategory")
    public Result getAllArticleCategory(){
        List<ArticleCategoriesVo> articleCategories = categoryService.findArticleCategories();
        return Result.succ(200, "请在下列文章圈子中选择一样", articleCategories);
    }

}
