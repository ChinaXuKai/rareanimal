package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xukai
 * @create 2023-03-25 15:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalIntroduceImg {

    private Long id;

    private Long animalId;

    private String morphologyImg;

    private String habitatImg;

    private String habitsImg;

    private String populationStatusImg;

    private String protectionLevelImg;

    private String elseInfoImg;

}
