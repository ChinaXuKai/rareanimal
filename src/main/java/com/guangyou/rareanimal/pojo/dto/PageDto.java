package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2022-12-31 11:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "分页显示需要的相关数据")
public class PageDto {

    @ApiModelProperty(value = "当前第几页")
    private Integer page = 1;

    @ApiModelProperty(value = "每页要多少数据")
    private Integer pageSize = 10;
}
