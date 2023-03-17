package com.guangyou.rareanimal.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author xukai
 * @create 2023-03-06 8:37
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AnimalLabel {

    private Long id;

    private Long animalId;

    private String animalLabel;
}
