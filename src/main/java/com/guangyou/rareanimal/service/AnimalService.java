package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.vo.Animal;
import com.guangyou.rareanimal.pojo.vo.AnimalIntroduce;
import com.guangyou.rareanimal.pojo.vo.AnimalIntroduceImgVo;

import java.util.List;

/**
 * @author xukai
 * @create 2022-12-14 23:20
 */
public interface AnimalService {

    /**
     *查询所有的两栖类动物的信息
     * @return 所有的两栖类动物的信息的集合
     */
    List<Animal> selectAmphibiansAnimal();

    /**
     * 查询所有的鱼类动物的信息
     * @return 所有的鱼类动物的信息的集合
     */
    List<Animal> selectFishAnimal();

    /**
     * 查询所有的脊椎动物的信息
     * @return 所有的脊椎动物的信息的集合
     */
    List<Animal> selectVertebratesAnimalsInfo();

    /**
     * 查询所有的无脊椎动物的信息
     * @return 所有的无脊椎动物的信息的集合
     */
    List<Animal> selectNoVertebratesAnimalsInfo();

    /**
     * 查询所有的哺乳动物的信息
     * @return 所有的哺乳动物的信息的集合
     */
    List<Animal> selectSuckleAnimalsInfo();

    /**
     * 查询所有的鸟类动物的信息
     * @return 所有的鸟类动物的信息的集合
     */
    List<Animal> selectBirdAnimalsInfo();

    /**
     * 查询所有的爬行动物的信息
     * @return 所有的爬行动物的信息的集合
     */
    List<Animal> selectCreepAnimalsInfo();

    /**
     * 根据animalId查询AnimalIntroduce的信息
     * @param animalId 所需要查询的animalId
     * @return
     */
    AnimalIntroduce selectAnimalsSimpInfo(Integer animalId);

    /**
     * 根据 animalLike 进行模糊查询动物
     * @param animalLike 模糊的查询：动物名称、动物标签
     * @return
     */
    List<Animal> selectAnimalByLike(String animalLike);

    /**
     * 返回 randAnimalNumber 个随机动物数据
     * @param randAnimalNumber 随机动物数据的个数
     * @return
     */
    List<Animal> selectRandAnimalInfo(Integer randAnimalNumber);

    /**
     * 根据动物id 查询对应动物
     * @param animalId
     * @return
     */
    Animal selectAnimalById(Long animalId);

}
