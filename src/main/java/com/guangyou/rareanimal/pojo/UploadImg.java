package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-03-14 10:48
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UploadImg {

    private Long id;

    private Integer isUserAvatarImg;

    private Long userId;

    private Integer isArticleContentImg;

    private String imgUrl;
}
