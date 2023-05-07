package com.guangyou.rareanimal.pojo.dto;

import com.guangyou.rareanimal.pojo.Question;
import com.guangyou.rareanimal.pojo.vo.QuestionVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-04-25 9:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "问题分页的相关参数")
public class QuestionPageDto {

    private static final Integer NOT_SORT = 0;

    @ApiModelProperty(value = "分页参数")
    private PageDto pageDto;

    @ApiModelProperty(value = "是否按时间排序")
    private Integer isNew = NOT_SORT;

    @ApiModelProperty(value = "是否按紧急情况排序")
    private Integer isUrgent = NOT_SORT;

    @ApiModelProperty(value = "是否按已完成排序")
    private Integer isFinish = NOT_SORT;

    @ApiModelProperty(value = "是否按热度排序")
    private Integer isTop = NOT_SORT;

}
