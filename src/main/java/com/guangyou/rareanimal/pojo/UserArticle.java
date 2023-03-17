package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-01-11 11:17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserArticle {

    private Long id;

    private Long userId;

    private Long articleId;

}
