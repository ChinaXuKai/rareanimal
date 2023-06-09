package com.guangyou.rareanimal.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-03-31 21:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    @TableId(value = "question_id",type = IdType.AUTO)
    private Long questionId;

    private Integer userId;

    private String questionTitle;

    private String questionDescribe;

    private Long publishTime;

    private Long updateTime;

    private Integer isUrgent;

    private Integer isFinish;
}
