package com.guangyou.rareanimal.pojo.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author xukai
 * @create 2022-12-31 16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "文章圈子")
public class ArticleCategoriesVo {

    @ApiModelProperty(value = "文章圈子主题")
    private String theme;

    @ApiModelProperty(value = "文章圈子主题的主键id")
    private Long themeId;

    @ApiModelProperty(value = "该主题下的所有圈子")
    private List<CategoryVo> articleCategories;

}
