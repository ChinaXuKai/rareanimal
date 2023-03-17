package com.guangyou.rareanimal.pojo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.guangyou.rareanimal.pojo.vo.ArticleBodyVo;
import com.guangyou.rareanimal.pojo.vo.CategoryVo;
import com.guangyou.rareanimal.pojo.vo.CustomTagVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author xukai
 * @create 2022-12-28 10:47
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel(description = "文章发表的所需相关参数")
public class ArticleDto {

    private final static String VISIT_PERMISSION_ALL = "全部可见";
    private final static String VISIT_PERMISSION_CARER = "关注可见";
    private final static String VISIT_PERMISSION_MY = "仅我可见";


    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "文章id")
    private Long id;

    @ApiModelProperty(value = "文章封面url地址")
    private List<String> articleCoverImgUrls;

    @Size(min = 0,max = 50,message = "文章标题限制在0~50位字符")
    @ApiModelProperty(value = "文章标题",required = true)
    private String title;

    @Size(min = 0,max = 255,message = "文章描述限制在0~512位字符")
    @ApiModelProperty(value = "文章摘要",required = true)
    private String summary;

    @ApiModelProperty(value = "文章内容")
    private ArticleBodyVo articleBody;

    @ApiModelProperty(value = "文章将所属的圈子")
    private ArticleBelongCategoryDto articleBelongCategory;

    @ApiModelProperty(value = "文章标签（可多个）")
    private List<String> tagsName;

    @ApiModelProperty(value = "文章类型")
    private String articleType;

    @ApiModelProperty(value = "阅读许可：全部可见、仅我可见、关注可见")
    private String visitPermission = VISIT_PERMISSION_ALL;

}
