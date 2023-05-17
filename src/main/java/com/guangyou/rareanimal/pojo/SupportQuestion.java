package com.guangyou.rareanimal.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-05-08 11:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupportQuestion {

    @TableId(value = "support_id",type = IdType.AUTO)
    private Long supportId;

    private Integer userId;

    private Long questionId;

}
