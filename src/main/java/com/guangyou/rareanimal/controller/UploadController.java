package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.service.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xukai
 * @create 2023-03-14 10:53
 */
@RestController
@RequestMapping("/upload")
@Slf4j
@Api(tags = "专门用于上传的上传接口系统（都不需要传jwt）")
public class UploadController {

    @Autowired
    private UploadService uploadService;


    @ApiOperation(value = "图片上传接口（不需要jwt）",notes = "用于上传用户头像或文章内容图的接口")
    @PostMapping("/img")
    public Result uploadImg(@RequestParam("img") MultipartFile img){
        String imgUrl = uploadService.uploadImg(img);
        if (imgUrl == null){
            throw new RuntimeException("上传失败");
        }else {
            return Result.succ(200, "上传成功", imgUrl);
        }
    }


}

