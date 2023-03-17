package com.guangyou.rareanimal.pojo;

import com.guangyou.rareanimal.pojo.vo.CategoryVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-03-09 16:32
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecommendCategory {
    private Long id;

    private Long categoryId;

    private String categoryLabel;

    private String categoryName;

    private Integer articleCount;

    private String categoryAvatar;

    private Long recommendTime;

    /**
     * recommend_factor = 所属该文章分类的总文章数
     */
    private Integer recommendFactor;

}
