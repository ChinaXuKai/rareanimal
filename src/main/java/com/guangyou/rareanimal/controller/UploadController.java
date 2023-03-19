package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.UploadImgDto;
import com.guangyou.rareanimal.service.UploadService;
import com.guangyou.rareanimal.utils.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xukai
 * @create 2023-03-14 10:53
 */
@RestController
@RequestMapping("/upload")
@Slf4j
@Api(tags = "专门用于上传的上传接口系统")
public class UploadController {

    @Autowired
    private UploadService uploadService;


    @ApiOperation(value = "图片上传接口（不需要jwt）",notes = "用于上传用户头像或文章内容图的接口")
    @PostMapping("/img")
    public Result uploadImg(@Validated UploadImgDto uploadImgDto, @RequestParam("img") MultipartFile img){
        Long userId = uploadImgDto.getUserId();
        Integer isArticleContentImg = uploadImgDto.getIsArticleContentImg();
        Integer isUserAvatarImg = uploadImgDto.getIsUserAvatarImg();
        Integer isArticleCoverImg = uploadImgDto.getIsArticleCoverImg();
        if ((isUserAvatarImg==0 && isArticleContentImg==0 && isArticleCoverImg==0) ||
                (isArticleCoverImg==1 && isArticleContentImg==1) ||
                (isUserAvatarImg==1 && userId==null)){
            return Result.fail("参数错误");
        }

        String imgUrl = uploadService.uploadImg(uploadImgDto,img);
        if (imgUrl == null){
            return Result.fail("上传失败");
        }else {
            return Result.succ(200, "上传成功", imgUrl);
        }
    }


}

