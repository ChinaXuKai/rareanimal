package com.guangyou.rareanimal.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2022-11-19 10:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "森林公安电话")
public class ForestPolicePhoneVo {

    @ApiModelProperty(value = "主键Id")
    private Integer phoneId;

    @ApiModelProperty(value = "当地的森林公安")
    private String forestPolice;

    @ApiModelProperty(value = "当地的森林公安电话")
    private String forestPolicePhone;

    @ApiModelProperty(value = "森林公安所在省级")
    private String forestPoliceProvince;

}
