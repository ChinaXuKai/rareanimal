package com.guangyou.rareanimal.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-01-06 9:54
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "发布文章、评论的用户信息")
public class AuthorVo {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "用户名ID")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    @ApiModelProperty(value = "用户头像，有默认头像，可修改")
    private String userAvatar;
}
