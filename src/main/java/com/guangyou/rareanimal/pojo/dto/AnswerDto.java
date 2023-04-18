package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * @author xukai
 * @create 2023-04-16 11:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "回答问题的所需的相关参数")
public class AnswerDto {

    @ApiModelProperty(value = "回答哪个问题的id")
    private Long questionId;

    @ApiModelProperty(value = "回答的内容")
    private String answerContent;

}
