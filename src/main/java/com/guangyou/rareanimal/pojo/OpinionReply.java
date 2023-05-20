package com.guangyou.rareanimal.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-05-18 9:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpinionReply {

    @TableId(value = "opinion_reply_id",type = IdType.AUTO)
    private Long opinionReplyId;

    private String replyContent;

    private Long opinionId;

    private Integer isDelete;

}
