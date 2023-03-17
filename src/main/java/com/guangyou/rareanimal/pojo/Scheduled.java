package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-02-10 10:42
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Scheduled {

    private Long cronId;

    private String cronName;

    private String cron;

}
