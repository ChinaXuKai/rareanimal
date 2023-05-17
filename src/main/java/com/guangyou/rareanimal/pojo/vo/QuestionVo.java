package com.guangyou.rareanimal.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xukai
 * @create 2023-04-16 15:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户问题的展示")
public class QuestionVo {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "问题主键id")
    private Long questionId;

    @ApiModelProperty(value = "发表该问题的用户信息")
    private AuthorInfoVo authorInfo;

    @ApiModelProperty(value = "问题内容")
    private String questionDescribe;

    @ApiModelProperty(value = "问题的标题")
    private String questionTitle;

    @ApiModelProperty(value = "问题标签集合")
    private List<String> questionTags;

    @ApiModelProperty(value = "发表的时间")
    private String publishTime;

    @ApiModelProperty(value = "修改的时间")
    private String updateTime;

    @ApiModelProperty(value = "是否紧急")
    private Integer isUrgent;
//
//    @ApiModelProperty(value = "该问题的所有回答")
//    private List<AnswerQuestionVo> answers;

    @ApiModelProperty(value = "该问题的点赞数")
    private Integer supportCount;

    @ApiModelProperty(value = "当前用户是否已经点赞了该问题。已点赞为1，没点赞为0")
    private Integer isSupport;

}
