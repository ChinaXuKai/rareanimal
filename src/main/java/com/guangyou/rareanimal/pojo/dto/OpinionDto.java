package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-04-13 9:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户意见发表与修改所需要的参数")
public class OpinionDto {

    @ApiModelProperty(value = "意见主键id")
    private Long opinionId;

    @ApiModelProperty(value = "意见内容")
    private String opinionContent;

}
