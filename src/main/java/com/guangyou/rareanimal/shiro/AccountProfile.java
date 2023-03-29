package com.guangyou.rareanimal.shiro;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Description 自定义 authentication 对象
 * @author xukai
 * @create 2022-11-05 13:03
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户信息")
public class AccountProfile implements Serializable {

    @ApiModelProperty(name = "用户名ID")
    private Integer userId;

    @ApiModelProperty(name = "用户名")
    private String userName;

    @ApiModelProperty(name = "用户昵称")
    private String userAccount;

    @ApiModelProperty(name = "用户密码")
    private String userPwd;

    @ApiModelProperty(name = "用户头像")
    private String userAvatar;

    @ApiModelProperty(name = "该用户账号创建的时间")
    private String createTime;

    @ApiModelProperty(name = "逻辑删除，默认值为0，删除用户时设置为1实现逻辑删除")
    private Integer isDelete;

    @ApiModelProperty(name = "用户定位地址")
    private String userAddress;

}
