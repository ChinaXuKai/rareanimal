package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * @author xukai
 * @create 2022-12-27 22:22
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    //审核状态：待审核、审核通过、审核不通过
    public static final String WAIT_AUDIT = "待审核";
    public static final String PASS_AUDIT = "审核通过";
    public static final String NO_PASS_AUDIT = "审核不通过";

    public static final int Article_TOP = 1;

    public static final int Article_Common = 0;

    private Long id;

    private String title;

    private String summary;

    private Integer commentCounts;

    private Integer viewCounts;

    private Integer supportCounts;

    private Integer saveCounts;

    /**
     * 作者账号
     */
    private String authorAccount;
    /**
     * 内容id
     */
    private Long bodyId;
    /**
     *圈子id
     */
    private Long categoryId;
    /**
     * 类型
     */
    private String articleType;
    /**
     * 访问权限：全部可见、关注可见、仅我可见
     */
    private String visitPermission;
    /**
     * 置顶
     */
    private Integer weight = Article_Common;
    /**
     * 创建时间
     */
    private Long createDate;
    /**
     * 逻辑删除
     * 1：逻辑删除
     */
    private Integer isDelete;
    /**
     * 是否已经展示
     * 1：已经展示
     */
    private Integer isRead;
    /**
     * 审核状态：待审核、审核通过、审核不通过
     */
    private String auditState;

}
