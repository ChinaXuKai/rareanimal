package com.guangyou.rareanimal.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-04-12 21:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityJoin {

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long activityId;

}
