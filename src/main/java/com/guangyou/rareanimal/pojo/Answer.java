package com.guangyou.rareanimal.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-04-16 11:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @TableId(value = "answer_id",type = IdType.AUTO)
    private Long answerId;

    private Long userId;

    private Long questionId;

    private String answerContent;
}
