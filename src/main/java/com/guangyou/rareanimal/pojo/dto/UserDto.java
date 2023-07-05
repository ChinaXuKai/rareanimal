package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Size;

/**
 * @author xukai
 * @create 2022-11-01 11:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "用户登录参数和注册参数")
public class UserDto {


    @Size(min = 6,max = 13,message = "用户账号限制在6~13位")
    @ApiModelProperty(value = "用户账号", required = true,notes = "用户账号限制在3~20位")
    private String userAccount;

    @Size(min = 6,max = 13,message = "用户账号限制在6~13位")
    @ApiModelProperty(value = "用户密码",required = true,notes = "密码限制在6~20位")
    private String userPwd;

}
