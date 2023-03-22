package com.guangyou.rareanimal.service.impl;

import com.guangyou.rareanimal.service.UploadService;
import com.guangyou.rareanimal.utils.FtpUtil;
import com.guangyou.rareanimal.utils.IDUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author xukai
 * @create 2023-03-14 11:02
 */
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    private FtpUtil ftpUtil;
    @Override
    public String uploadImg(MultipartFile img) {
        //1、给上传的图片生成新的文件名
        //1.1获取原始文件名
        String oldName = img.getOriginalFilename();
        //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
        String newName = IDUtil.genImageName();
        assert oldName != null;
        newName = newName + oldName.substring(oldName.lastIndexOf("."));
        //1.3生成文件在服务器端存储的子目录
        String filePath = new DateTime().toString("/yyyyMMdd/");

        //2、把图片上传到图片服务器
        //2.1获取上传的io流
        InputStream input = null;
        try {
            input = img.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //2.2调用FtpUtil工具类进行上传，并返回图片地址
        return ftpUtil.putImages(input, filePath, newName);

    }


//    @Override
//    public String uploadVideo(MultipartFile video) {
//        //1、给上传的图片生成新的文件名
//        //1.1获取原始文件名
//        String oldName = video.getOriginalFilename();
//        //1.2使用IDUtils工具类生成新的文件名，新文件名 = newName + 文件后缀
//        String newName = IDUtil.genImageName();
//        assert oldName != null;
//        newName = newName + oldName.substring(oldName.lastIndexOf("."));
//        //1.3生成文件在服务器端存储的子目录
//        String filePath = new DateTime().toString("/yyyyMMdd/");
//
//        //2、把图片上传到图片服务器
//        //2.1获取上传的io流
//        InputStream input = null;
//        try {
//            input = video.getInputStream();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //2.2调用FtpUtil工具类进行上传，并返回图片地址
//        return ftpUtil.putImages(input, filePath, newName);
//    }
}
