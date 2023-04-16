package com.guangyou.rareanimal.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-03-19 17:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ApiModel("作者作展示的相关信息")
public class AuthorInfoVo {

    @ApiModelProperty(value = "作者id")
    private Long authorId;

    @ApiModelProperty(value = "作者的昵称")
    private String authorName;

    @ApiModelProperty(value = "作者的账号")
    private String authorAccount;

    @ApiModelProperty(value = "作者的头像url地址")
    private String authorAvatarUrl;

    @ApiModelProperty(value = "作者的粉丝数")
    private Integer fansCount;

    @ApiModelProperty(value = "作者的关注数")
    private Integer careCount;

    @ApiModelProperty(value = "作者的社交动态数",notes = "即所发表的文章、问题总数")
    private Integer socialCount;

    @ApiModelProperty(value = "该用户的创建时间")
    private String createTime;

    @ApiModelProperty(value = "用于判别该用户是否已经被当前用户所关注",notes = "为1表示之前已经关注，为0表示之前并未关注")
    private Integer isCared;
}
