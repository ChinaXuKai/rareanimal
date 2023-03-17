package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-01-03 14:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {

    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private String authorAccount;

    private Long parentId;

    private Long toUid;

    private Integer level;

}
