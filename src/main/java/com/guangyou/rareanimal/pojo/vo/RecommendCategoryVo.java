package com.guangyou.rareanimal.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-03-09 16:28
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "推荐的文章的圈子")
public class RecommendCategoryVo {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "文章圈子id")
    private Long categoryId;

    @ApiModelProperty(value = "文章圈子的标签")
    private String categoryLabel;

    @ApiModelProperty(value = "文章圈子的名称")
    private String categoryName;

    @ApiModelProperty(value = "文章圈子现有的文章数量")
    private Long articleCount;

    @ApiModelProperty(value = "文章圈子的图标")
    private String categoryAvatar;

    @ApiModelProperty(value = "记录推荐时间")
    private String recommendTime;

}
