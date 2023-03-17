package com.guangyou.rareanimal.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2022-11-19 9:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "野生动物救助中心电话")
public class AnimalRescuePhoneVo {

    @ApiModelProperty(value = "主键Id")
    private Integer phoneId;

    @ApiModelProperty(value = "救助中心")
    private String rescueCenter;

    @ApiModelProperty(value = "救助电话")
    private String rescuePhone;

    @ApiModelProperty(value = "救助中心所在省级")
    private String rescueCenterProvince;

}
