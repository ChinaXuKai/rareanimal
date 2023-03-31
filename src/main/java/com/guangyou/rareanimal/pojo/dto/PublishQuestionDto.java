package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-03-30 19:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "发表问题所需参数")
public class PublishQuestionDto {

    @ApiModelProperty(value = "发表该问题的用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户的问题")
    private String question;

//    @ApiModelProperty(value = "")

}
