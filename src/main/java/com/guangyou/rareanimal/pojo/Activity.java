package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-04-12 20:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    private Long activityId;

    private Long publishUid;

    private String activityTitle;

    private String activityDescribe;

    private String activityPlace;

    private Long publishTime;

    private Long endTime;

    private Long updateTime;

}
