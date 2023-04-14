package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.vo.Animal;
import com.guangyou.rareanimal.pojo.vo.AnimalIntroduce;
import com.guangyou.rareanimal.pojo.vo.AnimalIntroduceImgVo;
import com.guangyou.rareanimal.service.AnimalService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xukai
 * @create 2022-12-14 23:12
 */
@Slf4j
@RestController
@RequestMapping("/animal")
@Api(tags = "动物相关接口（都不需要jwt）")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    private final static Integer RAND_ANIMAL_NUMBER = 25;

    @ApiOperation(value = "随机返回动物数据",notes = "随机返回25条动物的相关数据：id、animalName、animalImg")
    @GetMapping("/getRandAnimalInfo")
    public Result getRandAnimalInfo(){
        List<Animal> randAnimals = animalService.selectRandAnimalInfo(RAND_ANIMAL_NUMBER);
        //因为只需要返回id、animal、animalImg，所以不用给 animalIntroduce 属性赋值
        return Result.succ(200, "已返回随机动物数据", randAnimals);
    }


    @ApiOperation(value = "根据动物名称进行模糊查询动物",notes = "查询特定名称动物的相关信息（模糊查询）")
    @GetMapping("/getAnimalInfo")
    public Result getAnimalInfo(String animalLike){
        List<Animal> likeAnimals = animalService.selectAnimalByLike(animalLike);

        if (likeAnimals.isEmpty()){
            return Result.fail(Result.FORBIDDEN,"未查找到你指定的动物",null);
        }else {
            return Result.succ(200,"已查找到你指定的动物",likeAnimals);
        }
    }

    @ApiOperation(value = "根据动物id获取动物详情数据",notes = "根据动物id获取动物详情数据")
    @GetMapping("/getAnimalById/{animalId}")
    public Result getAnimalById(@PathVariable("animalId") Long animalId){
        Animal animal = animalService.selectAnimalById(animalId);
        setAnimalSimpInfo(animal);
        return Result.succ(200, animal.getAnimalName() + "详情如下", animal);
    }

    /**
     *
     * 为每个animal的 animalIntroduce 赋值
     * @param animalList animalIntroduce需要被赋值的动物集合
     * @return
     */
    private List<Animal> setAnimalListSimpInfo(List<Animal> animalList){
        //循环为每个animal的animalIntroduce赋值
        for (Animal animal : animalList){
            setAnimalSimpInfo(animal);
        }
        return animalList;
    }
    /**
     * 为单个animal的 animalIntroduce 赋值
     * @param animal animalIntroduce需要被赋值的动物
     * @return
     */
    private Animal setAnimalSimpInfo(Animal animal){
        //根据animalId查询AnimalIntroduce的信息
        AnimalIntroduce animalIntroduce = animalService.selectAnimalsSimpInfo(animal.getAnimalId());
        animal.setAnimalIntroduce(animalIntroduce);
        log.info("info={}",animal.getAnimalIntroduce());
        return animal;
    }

    @ApiOperation(value = "脊椎动物",notes = "有脊椎动物")
    @GetMapping("/getVertebratesAnimalInfo")
    public Result getVertebratesAnimalInfo(){
        List<Animal> animalList = animalService.selectVertebratesAnimalsInfo();

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的脊椎动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的脊椎动物~", animalList);
        }
    }


    @ApiOperation(value = "无脊椎动物",notes = "无脊椎动物")
    @GetMapping("/getNoVertebratesAnimalInfo")
    public Result getNoVertebratesAnimalInfo(){
        List<Animal> animalList = animalService.selectNoVertebratesAnimalsInfo();

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的无脊椎动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的无脊椎动物~", animalList);
        }
    }


    @ApiOperation(value = "哺乳动物",notes = "哺乳动物")
    @GetMapping("/getSuckleAnimalInfo")
    public Result getSuckleAnimalInfo(){
        List<Animal> animalList = animalService.selectSuckleAnimalsInfo();

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的哺乳动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的哺乳动物~", animalList);
        }
    }


    @ApiOperation(value = "鸟类动物",notes = "鸟类动物")
    @GetMapping("/getBirdAnimalInfo")
    public Result getBirdAnimalInfo(){
        List<Animal> animalList = animalService.selectBirdAnimalsInfo();

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的鸟类动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的鸟类动物~", animalList);
        }
    }


    @ApiOperation(value = "爬行动物",notes = "爬行动物")
    @GetMapping("/getCreepAnimalInfo")
    public Result getCreepAnimalInfo(){
        List<Animal> animalList = animalService.selectCreepAnimalsInfo();

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的爬行动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的爬行动物~", animalList);
        }
    }

    @ApiOperation(value = "鱼类动物",notes = "鱼类动物")
    @GetMapping("/getFishAnimal")
    public Result getFishAnimal(){
        List<Animal> animalList = animalService.selectFishAnimal();

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的鱼类动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的鱼类动物~", animalList);
        }
    }

    @ApiOperation(value = "两栖类动物",notes = "两栖类动物")
    @GetMapping("/getAmphibiansAnimal")
    public Result getAmphibiansAnimal(){
        List<Animal> animalList = animalService.selectAmphibiansAnimal();

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的两栖类动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的两栖类动物~", animalList);
        }
    }
}
