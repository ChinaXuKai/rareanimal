package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-03-15 19:05
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RecommendArticle {

    private Long id;

    private Integer recommendFactor;

    private Long articleId;

    private Long recommendTime;
}
