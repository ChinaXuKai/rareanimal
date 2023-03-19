package com.guangyou.rareanimal.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * @author xukai
 * @create 2023-03-15 19:07
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "推荐文章")
public class RecommendArticleVo {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键id")
    private Long id;

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "文章id")
    private Long articleId;

    @ApiModelProperty(value = "文章封面url地址")
    private List<String> coverImg;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "作者的账号昵称")
    private AuthorInfoVo authorInfo;

    @ApiModelProperty(value = "评论数量")
    private Integer commentCounts;

    @ApiModelProperty(value = "阅读数量")
    private Integer viewCounts;

    @ApiModelProperty(value = "点赞数量")
    private Integer supportCounts;

    @ApiModelProperty(value = "收藏数量")
    private Integer saveCounts;

    @ApiModelProperty(value = "推荐时间")
    private String recommendTime;

}
