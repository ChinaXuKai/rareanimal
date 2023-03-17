package com.guangyou.rareanimal.pojo.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "动物的简介：形态特征、栖息环境、生活习性、种群现状、保护等级、其他相关信息")
public class AnimalIntroduce {

    @ApiModelProperty(value = "主键Id")
    private Integer animalId;

    @ApiModelProperty(value = "形态特征")
    private String morphology;

    @ApiModelProperty(value = "栖息环境")
    private String habitat;

    @ApiModelProperty(value = "生活习性")
    private String habits;

    @ApiModelProperty(value = "种群现状")
    private String populationStatus;

    @ApiModelProperty(value = "保护等级")
    private String protectionLevel;

    @ApiModelProperty(value = "其他相关信息")
    private String elseInfo;

}
