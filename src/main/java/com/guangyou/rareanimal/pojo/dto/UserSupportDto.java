package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-01-11 17:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "用户为文章或评论的点赞所需要传的参数")
public class UserSupportDto {

    @ApiModelProperty(value = "被点赞的文章id，为null说明用户点赞的是评论")
    private Long articleId;

    @ApiModelProperty(value = "被点赞的评论id，为null说明用户点赞的是文章")
    private Long commentId;

}
