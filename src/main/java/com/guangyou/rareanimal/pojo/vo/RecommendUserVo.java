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
 * @create 2023-03-10 10:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "推荐用户")
public class RecommendUserVo {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键id")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "用户昵称")
    private String userName;

    @ApiModelProperty(value = "用户密码")
    private String userAvatar;

    @ApiModelProperty(value = "用户的粉丝数")
    private Integer carerCount;

    @ApiModelProperty(value = "推荐时间")
    private String recommendTime;
}
