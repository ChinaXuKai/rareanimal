package com.guangyou.rareanimal.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author xukai
 * @create 2023-03-14 11:01
 */
public interface UploadService {
    /**
     * 上传图片
     * @return 上传图片的url地址
     */
    String uploadImg(MultipartFile img);

    /**
     * 上传视频
     * @param video 上传的视频
     * @return
     */
    String uploadVideo(MultipartFile video);
}
