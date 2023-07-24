package com.guangyou.rareanimal.service.impl;

import com.guangyou.rareanimal.mapper.AnimalLabelMapper;
import com.guangyou.rareanimal.mapper.AnimalMapper;
import com.guangyou.rareanimal.pojo.vo.Animal;
import com.guangyou.rareanimal.pojo.vo.AnimalIntroduce;
import com.guangyou.rareanimal.pojo.vo.AnimalLabelVo;
import com.guangyou.rareanimal.service.AnimalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xukai
 * @create 2022-12-14 23:20
 */
@Service
@Slf4j
public class AnimalServiceImpl implements AnimalService {

    @Autowired
    private AnimalMapper animalMapper;

    @Autowired
    private AnimalLabelMapper animalLabelMapper;

    /**
     * 将数据库里的 单个动物标签 拆分成 List<String>类型的多个动物标签
     * @param animalList
     * @return
     */
    private void getAnimalLabels(List<Animal> animalList){
        for (Animal animal : animalList){
            Integer animalId = animal.getAnimalId();
            AnimalLabelVo animalLabelVo = animalLabelMapper.selectAnimalLabelById(animalId.longValue());
            String[] animalLabels = animalLabelVo.getAnimalLabel().split("、");
            List<String> labelList = new ArrayList<>(Arrays.asList(animalLabels));
            animal.setAnimalLabel(labelList);
        }
    }

    @Override
    public List<Animal> selectAmphibiansAnimal() {
        List<Animal> amphibianAnimals = animalMapper.selectAmphibianAnimals();
        getAnimalLabels(amphibianAnimals);
        return amphibianAnimals;
    }

    @Override
    public List<Animal> selectFishAnimal() {
        List<Animal> fishAnimals = animalMapper.selectFishAnimals();
        getAnimalLabels(fishAnimals);
        return fishAnimals;
    }

    @Override
    public List<Animal> selectVertebratesAnimalsInfo() {
        List<Animal> vertebratesAnimalList = animalMapper.selectVertebratesAnimals();
        getAnimalLabels(vertebratesAnimalList);
        return vertebratesAnimalList;
    }

    @Override
    public List<Animal> selectNoVertebratesAnimalsInfo() {
        List<Animal> noVertebratesAnimalList = animalMapper.selectNoVertebratesAnimals();
        getAnimalLabels(noVertebratesAnimalList);
        return noVertebratesAnimalList;
    }

    @Override
    public List<Animal> selectSuckleAnimalsInfo() {
        List<Animal> suckleAnimalList = animalMapper.selectSuckleAnimals();
        getAnimalLabels(suckleAnimalList);
        return suckleAnimalList;
    }

    @Override
    public List<Animal> selectBirdAnimalsInfo() {
        List<Animal> birdAnimalList = animalMapper.selectBirdAnimalInfo();
        getAnimalLabels(birdAnimalList);
        return birdAnimalList;
    }

    @Override
    public List<Animal> selectCreepAnimalsInfo() {
        List<Animal> creepAnimalList = animalMapper.selectCreepAnimals();
        getAnimalLabels(creepAnimalList);
        return creepAnimalList;
    }

    @Override
    public AnimalIntroduce selectAnimalsSimpInfo(Integer animalId) {
        return animalMapper.selectAnimalIntroduceById(animalId);
    }

    @Override
    public List<Animal> selectAnimalByLike(String animalLike) {
        List<Animal> animalsByLike = animalMapper.selectAnimalByLikeName("%" + animalLike + "%");
        getAnimalLabels(animalsByLike);
        return animalsByLike;
    }

    @Override
    public List<Animal> selectRandAnimalInfo(Integer randAnimalNumber) {
        return animalMapper.selectRandAnimalInfo(randAnimalNumber);
    }

    @Override
    public Animal selectAnimalById(Long animalId) {
        return animalMapper.selectAnimalById(animalId);
    }

}
