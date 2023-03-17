package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-01-22 12:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserCarer {

    private Long id;

    private  Long userId;

    private Long CarerId;

}
