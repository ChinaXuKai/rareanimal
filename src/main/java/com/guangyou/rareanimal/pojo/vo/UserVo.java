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
 * @create 2023-01-23 15:05
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "用户信息")
public class UserVo {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "用户名ID")
    private Integer userId;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "用户账号")
    private String userAccount;

    @ApiModelProperty(value = "用户密码")
    private String userPwd;

    @ApiModelProperty(value = "用户头像，有默认头像，可修改")
    private String userAvatar;

    @ApiModelProperty(value = "该用户账号创建的时间")
    private String createTime;

    @ApiModelProperty(value = "用户定位地址，暂不实现（设置默认值）")
    private String userAddress;

    @ApiModelProperty(value = "用户的粉丝数")
    private Integer fanCounts;

    @ApiModelProperty(value = "用户的关注数")
    private Integer careCounts;

    @ApiModelProperty(value = "用户发表的文章篇数")
    private Integer articleCounts;

}
