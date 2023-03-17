package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-02-12 19:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "文章所属的圈子")
public class ArticleBelongCategoryDto {

    @ApiModelProperty(value = "圈子的主键id")
    private Long categoryId;

}
