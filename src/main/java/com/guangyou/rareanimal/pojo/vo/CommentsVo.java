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

/**
 * @author xukai
 * @create 2022-12-27 23:12
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "评论信息")
public class CommentsVo {

    //防止前端精度损失，把id转为string
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "此评论的用户")
    private AuthorVo author;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "子评论")
    private List<CommentsVo> childrenComments;

    @ApiModelProperty(value = "发表评论的时间")
    private String createDate;

    @ApiModelProperty(value = "该评论为第几层（1级表示最上层的评论，2表示对评论的评论，以此类推）")
    private Integer level;

    @ApiModelProperty(value = "此评论是给谁评论的，给文章评论则为null，给别人评论的评论则为userVo")
    private AuthorVo toUser;

    @ApiModelProperty(value = "点赞数量")
    private Integer supportCounts;


//    @ApiModelProperty(value = "主键id")
//    private Integer commentsId;
//
//    @ApiModelProperty(value = "评论内容")
//    private String commentContent;
//
//    @ApiModelProperty(value = "逻辑删除")
//    private Integer isDelete;
//
//    @ApiModelProperty(value = "评论所属用户账号")
//    private String userAccount;
//
//    @ApiModelProperty(value = "评论发表的时间")
//    private String publishTime;
//
//    @ApiModelProperty(value = "修改评论的时间")
//    private String updateTime;
//
//    @ApiModelProperty(value = "点赞的次数")
//    private Integer approveCount;
//
//    @ApiModelProperty(value = "踩次数")
//    private Integer disapproveCount;
//
//    @ApiModelProperty(value = "顶级评论")
//    private Integer rootCommentId;
//
//    @ApiModelProperty(value = "回复目标评论")
//    private Integer toCommentId;

}
