package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-05-22 14:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "提交知识问题选项的参数")
public class SubmitOptionDto {

    @ApiModelProperty(value = "所提交的知识问题的主键id")
    private Long knowledgeQuestionId;

    @ApiModelProperty(value = "用户所选择的选项：1/2/3/4 对应 A/B/C/D")
    private Integer userOption;

}
