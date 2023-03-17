package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-03-17 17:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ArticleCoverImg {

    private Long id;

    private Long articleId;

    private String coverImg;

}
