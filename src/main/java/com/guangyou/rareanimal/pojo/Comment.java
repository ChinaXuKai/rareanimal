package com.guangyou.rareanimal.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author xukai
 * @create 2023-01-03 14:30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private String authorAccount;

    private Long parentId;

    private Long toUid;

    private Integer level;

    private Integer isDelete;

}
