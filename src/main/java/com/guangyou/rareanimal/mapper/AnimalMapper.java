package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.vo.Animal;
import com.guangyou.rareanimal.pojo.vo.AnimalIntroduce;
import com.guangyou.rareanimal.pojo.vo.AnimalIntroduceImgVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2022-12-15 20:24
 */
@Repository
public interface AnimalMapper extends BaseMapper<Animal> {

    /**
     * 随机返回 randAnimalNumber 条动物数据
     * @param randAnimalNumber
     * @return
     */
    List<Animal> selectRandAnimalInfo(Integer randAnimalNumber);

    /**
     * 返回有脊椎动物
     * @return
     */
    List<Animal> selectVertebratesAnimals();

    /**
     * 返回无脊椎动物
     * @return
     */
    List<Animal> selectNoVertebratesAnimals();

    /**
     * 返回哺乳动物
     * @return
     */
    List<Animal> selectSuckleAnimals();

    /**
     * 返回鸟类动物
     * @return
     */
    List<Animal> selectBirdAnimalInfo();

    /**
     * 返回爬行动物
     * @return
     */
    List<Animal> selectCreepAnimals();

    /**
     * 返回鱼类动物
     * @return
     */
    List<Animal> selectFishAnimals();

    /**
     * 返回两栖类动物
     * @return
     */
    List<Animal> selectAmphibianAnimals();

    /**
     * 根据动物id查询 对应动物信息
     * @return
     */
    AnimalIntroduce selectAnimalIntroduceById(Integer animalId);

    /**
     * 根据 模糊条件 查询相关动物
     * @return
     */
    List<Animal> selectAnimalByLikeName(String animalName);

    /**
     * 根据 动物id 查询对应动物
     * @return
     */
    Animal selectAnimalById(Long animalId);

}
