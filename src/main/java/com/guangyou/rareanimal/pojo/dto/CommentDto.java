package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-01-06 16:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "评论发表需要的相关数据")
public class CommentDto {

    @ApiModelProperty(value = "文章id")
    private Long articleId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "父评论的评论id")
    private Long parentId;

    @ApiModelProperty(value = "父评论的用户id")
    private Long toUserId;

}
