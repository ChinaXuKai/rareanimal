package com.guangyou.rareanimal.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-04-24 13:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "确认问题被答复 或 取消确认问题被答复")
public class ConfirmAnswerDto {

    @ApiModelProperty(value = "问题的id")
    private Long questionId;

    @ApiModelProperty(value = "被确认回答的id")
    private Long answerId;


}
