package com.guangyou.rareanimal.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-05-20 11:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "知识问题信息")
public class KnowledgeQuestionVo {

    @ApiModelProperty(value = "问题类型")
    private String questionType;

    @ApiModelProperty(value = "问题内容")
    private String questionContent;

    @ApiModelProperty(value = "正确选项：1 / 2 / 3 / 4")
    private Integer correctOption;

    @ApiModelProperty(value = "选项1 的内容")
    private String contentOfOptionA;

    @ApiModelProperty(value = "选项2 的内容")
    private String contentOfOptionB;

    @ApiModelProperty(value = "选项3 的内容")
    private String contentOfOptionC;

    @ApiModelProperty(value = "选项4 的内容")
    private String contentOfOptionD;

}
