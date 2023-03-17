package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-01-11 17:22
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserSupport {

    private Long id;

    private Long userId;

    private Long articleId;

    private Long commentId;

}
