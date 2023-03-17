package com.guangyou.rareanimal.pojo.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-03-06 8:38
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "动物的相关标签")
public class AnimalLabelVo {

    @ApiModelProperty(value = "动物id")
    private Long animalId;

    @ApiModelProperty(value = "动物id对应的标签")
    private String animalLabel;

}
