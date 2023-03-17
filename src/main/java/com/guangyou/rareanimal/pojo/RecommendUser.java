package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-03-10 10:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RecommendUser {

    private Long id;

    private Long userId;

    private String userName;

    private String userAvatar;

    private Long recommendTime;

    /**
     * recommend_factor = （粉丝数*0.5 + 发表文章数*0.5） * 100
     */
    private Integer recommendFactor;
}
