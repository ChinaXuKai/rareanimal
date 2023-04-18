package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xukai
 * @create 2023-04-17 14:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "发布文章和修改活动所需的参数")
public class ActivityDto {

    @ApiModelProperty(value = "活动主键id")
    private Long activityId;

    @ApiModelProperty(value = "活动标题")
    private String activityTitle;

    @ApiModelProperty(value = "活动描述",notes = "不得超过1024个字")
    private String activityDescription;

    @ApiModelProperty(value = "活动地址")
    private String activityPlace;

    @ApiModelProperty(value = "活动标签集合")
    private List<String> activityTags;

    @ApiModelProperty(value = "活动封面")
    private String coverUrl;

    @ApiModelProperty(value = "活动开始时间")
    private String startTime;

    @ApiModelProperty(value = "活动截止时间")
    private String endTime;

}
