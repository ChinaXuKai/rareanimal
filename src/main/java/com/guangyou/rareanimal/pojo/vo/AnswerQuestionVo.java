package com.guangyou.rareanimal.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-04-24 16:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "问题回答所展示的信息")
public class AnswerQuestionVo {

    @ApiModelProperty(value = "回答id")
    private Long answerId;

    @ApiModelProperty(value = "用户信息")
    private AuthorVo author;

    @ApiModelProperty(value = "对应的问题id")
    private Long questionId;

    @ApiModelProperty(value = "回答内容")
    private String answerContent;

    @ApiModelProperty(value = "是否被提问者评选为最佳答案",notes = "为true则表示该回答为最佳回答")
    private Boolean isPerfectAnswer;

}
