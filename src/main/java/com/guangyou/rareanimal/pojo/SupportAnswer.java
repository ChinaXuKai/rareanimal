package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.security.DenyAll;

/**
 * @author xukai
 * @create 2023-05-08 11:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportAnswer {

    private Long supportId;

    private Integer userId;

    private Long answerId;

}
