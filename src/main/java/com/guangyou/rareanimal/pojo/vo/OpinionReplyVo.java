package com.guangyou.rareanimal.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-05-18 9:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "用户意见回复的展示")
public class OpinionReplyVo {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "回复的主键id")
    private Long opinionReplyId;
//
//    @ApiModelProperty(value = "")
//    private OpinionVo opinion;

    @ApiModelProperty(value = "回复的内容")
    private String replyContent;

}
