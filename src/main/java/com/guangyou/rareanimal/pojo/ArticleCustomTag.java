package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-01-08 16:04
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCustomTag {

    private Long id;

    private String customTagName;

    private Long articleId;

}
