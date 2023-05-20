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

    @ApiModelProperty(value = "活动封面url地址")
    private String activityCoverUrl;

    @ApiModelProperty(value = "活动发布者的信息")
    private UserVo publisherVo;

    @ApiModelProperty(value = "申请时间")
    private String requestTime;

    @ApiModelProperty(value = "活动开始时间")
    private String startTime;

    @ApiModelProperty(value = "截止时间")
    private String endTime;

    @ApiModelProperty(value = "修改时间")
    private String updateTime;

    @ApiModelProperty(value = "活动封面")
    private String coverUrl;

    @ApiModelProperty(value = "标签描述集合")
    private List<String> tagsDescribe;

    @ApiModelProperty(value = "活动参与的人数")
    private Integer joinCount;

    @ApiModelProperty(value = "审核状态：待审核、审核通过、审核不通过")
    private String auditState;

    @ApiModelProperty(value = "审核所给的原因")
    private String auditReason;

    @ApiModelProperty(value = "活动参与人数的上限")
    private Integer peopleCeiling;

    @ApiModelProperty(value = "是否火热")
    private Boolean isHot;

    @ApiModelProperty(value = "是否截止")
    private Boolean isEnd;
}
