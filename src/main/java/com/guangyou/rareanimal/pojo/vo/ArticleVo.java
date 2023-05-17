package com.guangyou.rareanimal.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "文章信息")
public class ArticleVo {

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "文章封面url地址")
    private List<String> coverImg;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章简介")
    private String summary;

    @ApiModelProperty(value = "评论数量")
    private Integer commentCounts;

    @ApiModelProperty(value = "阅读数量")
    private Integer viewCounts;

    @ApiModelProperty(value = "点赞数量")
    private Integer supportCounts;

    @ApiModelProperty(value = "收藏数量")
    private Integer saveCounts;

    @ApiModelProperty(value = "文章类型：原创或转载")
    private String articleType;

    @ApiModelProperty(value = "是否置顶")
    private Integer weight;

    @ApiModelProperty(value = "该文章的发表时间")
    private String createDate;

    @ApiModelProperty(value = "作者信息")
    private AuthorInfoVo authorInfo;

    @ApiModelProperty(value = "文章内容")
    private ArticleBodyVo body;

    @ApiModelProperty(value = "文章标签")
    private List<CustomTagVo> tags;

    @ApiModelProperty(value = "文章所属圈子")
    private CategoryVo category;

    @ApiModelProperty(value = "文章访问权限：全部可见、关注可见、仅我可见")
    private String visitPermission;

    @ApiModelProperty(value = "审核状态：待审核、审核通过、审核不通过")
    private String auditState;

    @ApiModelProperty(value = "用于判别该文章是否已经被当前用户点赞")
    private Integer isSupport;

}
