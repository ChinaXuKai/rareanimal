package com.guangyou.rareanimal.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-22 17:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "用户关注列表信息")
public class UserCarerVo {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "用户id",notes = "关注者的用户id")
    private Long userId;

    @ApiModelProperty(value = "用户所关注的博主的分页数据")
    private PageDataVo<CaredBloggerVo> pageBloggerData;

//    @ApiModelProperty(value = "用户集合",notes = "所关注的博主集合")
//    private List<CaredBloggerVo> bloggerList;

}
