package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.vo.Animal;
import com.guangyou.rareanimal.pojo.vo.AnimalIntroduce;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2022-12-15 20:24
 */
@Repository
public interface AnimalMapper extends BaseMapper<Animal> {

    List<Animal> selectRandAnimalInfo(Integer randAnimalNumber);

    List<Animal> selectVertebratesAnimals();

    List<Animal> selectNoVertebratesAnimals();

    List<Animal> selectSuckleAnimals();

    List<Animal> selectBirdAnimalInfo();

    List<Animal> selectCreepAnimals();

    AnimalIntroduce selectAnimalIntroduceById(Integer animalId);

    List<Animal> selectAnimalByLikeName(String animalName);

}
