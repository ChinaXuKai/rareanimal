package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;

/**
 * @author xukai
 * @create 2023-03-14 10:56
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "上传图片接口所需的传参参数")
public class UploadImgDto {

    private final static int IS_USER_AVATAR_IMG = 0;
    private final static int IS_ARTICLE_CONTENT_IMG = 0;
    private final static int IS_ARTICLE_COVER_IMG = 0;

    @ApiModelProperty(value = "是否是上传用户头像")
    @Min(value = 0,message = "最小值为0")
    @Max(value = 1,message = "最大值为1")
    private Integer isUserAvatarImg = IS_USER_AVATAR_IMG;

    @ApiModelProperty(value = "若上传用户头像，则说明用户id")
    private Long userId;

    @ApiModelProperty(value = "是否是上传文章内容图")
    @Min(value = 0,message = "最小值为0")
    @Max(value = 1,message = "最大值为1")
    private Integer isArticleContentImg = IS_ARTICLE_CONTENT_IMG;

    @ApiModelProperty(value = "是否是上传文章封面图")
    @Min(value = 0,message = "最小值为0")
    @Max(value = 1,message = "最大值为1")
    private Integer isArticleCoverImg = IS_ARTICLE_COVER_IMG;

}
