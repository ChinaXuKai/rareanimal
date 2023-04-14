package com.guangyou.rareanimal.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author xukai
 * @create 2023-04-12 21:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "活动展示层")
public class ActivityVo {

    @ApiModelProperty(value = "活动主键id")
    private Long activityId;

    @ApiModelProperty(value = "活动标题")
    private String activityTitle;

    @ApiModelProperty(value = "活动详情描述")
    private String activityDescribe;

    @ApiModelProperty(value = "活动地址")
    private String activityPlace;


    @ApiModelProperty(value = "活动发布者的信息")
    private UserVo publisherVo;

    @ApiModelProperty(value = "发布时间")
    private String publishTime;

    @ApiModelProperty(value = "截止时间")
    private String endTime;

    @ApiModelProperty(value = "修改时间")
    private String updateTime;

    @ApiModelProperty(value = "活动封面集合")
    private List<String> coversUrl;

    @ApiModelProperty(value = "标签描述集合")
    private List<String> tagsDescribe;

    @ApiModelProperty(value = "活动参与的人数")
    private Integer joinCount;

    @ApiModelProperty(value = "是否火热")
    private Boolean isHot;

}
