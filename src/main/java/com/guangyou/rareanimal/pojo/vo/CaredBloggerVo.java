package com.guangyou.rareanimal.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-01-22 18:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "被关注的博主信息")
public class CaredBloggerVo {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "博主的用户id",notes = "被关注者的用户id")
    private Long bloggerUserId;

    @ApiModelProperty(value = "博主名称",notes = "被关注者的昵称")
    private String bloggerName;

    @ApiModelProperty(value = "博主头像",notes = "被关注者的头像")
    private String bloggerAvatar;
//    @ApiModelProperty(value = "博主粉丝数",notes = "被关注者的被关注数量")
//    private Integer bloggerFansCounts;

}
