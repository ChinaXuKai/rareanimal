package com.guangyou.rareanimal.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
@author xukai
@create 2023-02-10 14:41
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "圈子相关信息")
public class CategoryVo {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "圈子的标签")
    private String categoryLabel;

    @ApiModelProperty(value = "圈子名称")
    private String categoryName;

    @ApiModelProperty(value = "圈子内文章数量")
    private Long articleCount;

    @ApiModelProperty(value = "圈子图标")
    private String categoryAvatar;
}
