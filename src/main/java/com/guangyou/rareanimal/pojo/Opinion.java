package com.guangyou.rareanimal.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author xukai
 * @create 2023-04-12 22:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Opinion implements Serializable {

    /**
     * 数据库自增 AUTO
     * 开发者无需赋值,自己根据当前表中id最大值自增+1
     * 手动赋值的话还是使用数据库id中最大值+1 的方式赋予id
     */
    @TableId(value = "opinion_id",type = IdType.AUTO)
    private Long opinionId;

    private Long userId;

    private String opinionContent;

    private Long submitTime;

    private Long updateTime;

    private Integer isDelete;

}
