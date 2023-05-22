package com.guangyou.rareanimal.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-05-17 18:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeQuestionType {

    @TableId(value = "question_type_id",type = IdType.AUTO)
    private Integer questionTypeId;

    private String type;

}
