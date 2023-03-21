package com.guangyou.rareanimal.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-03-19 17:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("作者作展示的相关信息")
public class AuthorInfoVo {

    @ApiModelProperty(value = "作者id")
    private Long authorId;

    @ApiModelProperty(value = "作者的昵称")
    private String authorName;

    @ApiModelProperty(value = "作者的账号")
    private String authorAccount;

    @ApiModelProperty(value = "作者的头像url地址")
    private String authorAvatarUrl;

}
