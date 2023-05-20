package com.guangyou.rareanimal.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-05-17 17:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KnowledgeQuestion {

    @TableId(value = "knowledge_question_id",type = IdType.AUTO)
    private Long knowledgeQuestionId;

    private String questionContent;

    private Long questionTypeId;

    private Integer correctOption;

    private String optionContentA;

    private String optionContentB;

    private String optionContentC;

    private String optionContentD;

}
