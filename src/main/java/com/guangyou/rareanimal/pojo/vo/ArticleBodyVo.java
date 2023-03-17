package com.guangyou.rareanimal.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * @author xukai
 * @create 2022-12-31 16:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "文章内容文本")
public class ArticleBodyVo {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "文章内容对应的文章id")
    private Long articleId;

    @NotBlank(message = "文章内容不能为空")
    @ApiModelProperty(value = "存放html格式的文章内容")
    private String contentHtml;
}
