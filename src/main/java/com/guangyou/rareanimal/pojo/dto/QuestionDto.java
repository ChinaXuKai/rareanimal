package com.guangyou.rareanimal.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xukai
 * @create 2023-03-30 19:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "发表问题和修改问题所需参数")
public class QuestionDto {

    @ApiModelProperty(value = "问题id",notes = "发表问题的时候不需要传参，修改问题的时候需要参数")
    private Long questionId;

    @ApiModelProperty(value = "问题的标题")
    private String questionTitle;

    @ApiModelProperty(value = "问题描述")
    private String questionDescribe;

    @ApiModelProperty(value = "问题所带的标签")
    private List<String> questionTags;

    @ApiModelProperty(value = "是否是紧急的：是传1，不是传0")
    private Integer isUrgent;

}
