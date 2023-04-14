package com.guangyou.rareanimal.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-04-13 9:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户意见展示")
public class OpinionVo {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "意见主键id")
    private Long opinionId;

    @ApiModelProperty(value = "意见内容")
    private String opinionContent;


    @ApiModelProperty(value = "用户作展示的信息")
    private UserVo userVo;

    @ApiModelProperty(value = "提交意见的时间")
    private String submitTime;

    @ApiModelProperty(value = "修改意见的时间")
    private String updateTime;

}
