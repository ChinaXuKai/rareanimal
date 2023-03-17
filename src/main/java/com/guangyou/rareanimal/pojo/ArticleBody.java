package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xu'kai
 * @create 2023-1-2 11:11
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ArticleBody {

    private Long id;
    private String contentHtml;
    private Long articleId;
}

