package com.guangyou.rareanimal.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author xukai
 * @create 2022-11-05 13:03
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    public static final String OFFICIAL_ACCOUNT = "POEAzsyxk";

    private Integer userId;

    private String userName;

    private String userAccount;

    private String userPwd;

    private String userAvatar;

    private Long createTime;

    private Integer isDelete;

    private String userAddress;

}
