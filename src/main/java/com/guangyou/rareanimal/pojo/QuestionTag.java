package com.guangyou.rareanimal.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-04-04 8:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionTag {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionTagId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long questionId;

    private String tagInfo;

}
