package com.guangyou.rareanimal.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-03-25 15:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "动物介绍对应的图片")
public class AnimalIntroduceImgVo {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "动物id")
    private Long animalId;

    @ApiModelProperty(value = "形态特征对应的图片")
    private String morphologyImg;

    @ApiModelProperty(value = "栖息环境对应的图片")
    private String habitatImg;

    @ApiModelProperty(value = "生活习性对应的图片")
    private String habitsImg;

    @ApiModelProperty(value = "种群现状对应的图片")
    private String populationStatusImg;

    @ApiModelProperty(value = "保护级别对应的图片")
    private String protectionLevelImg;

    @ApiModelProperty(value = "其他相关信息对应的图片")
    private String elseInfoImg;

}
