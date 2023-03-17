package com.guangyou.rareanimal.service;

import cn.hutool.core.map.MapUtil;
import com.guangyou.rareanimal.pojo.dto.UploadImgDto;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xukai
 * @create 2023-03-14 11:01
 */
public interface UploadService {
    /**
     * 上传图片
     * @param uploadImgDto 上传图片的参数
     * @return 上传图片的url地址
     */
    String uploadImg(UploadImgDto uploadImgDto, MultipartFile img);

    /**
     * 上传视频
     * @param video 上传的视频
     * @return
     */
    String uploadVideo(MultipartFile video);
}
