package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.vo.Animal;
import com.guangyou.rareanimal.pojo.vo.AnimalIntroduce;
import com.guangyou.rareanimal.service.AnimalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
            setAnimalListSimpInfo(likeAnimals);
            return Result.succ(200,"已查找到你指定的动物",likeAnimals);
        }
    }

    /**
     * 为每个animal的animalIntroduce赋值
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
     * 为单个animal的animalIntroduce赋值
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

    @ApiOperation(value = "脊椎动物-info",notes = "一级保护动物-有脊椎动物的信息")
    @GetMapping("/getVertebratesAnimalInfo")
    public Result getVertebratesAnimalInfo(){
        List<Animal> animalList = animalService.selectVertebratesAnimalsInfo();

        setAnimalListSimpInfo(animalList);

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的脊椎动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的脊椎动物~", animalList);
        }
    }


    @ApiOperation(value = "无脊椎动物-info",notes = "一级保护动物-无脊椎动物的信息")
    @GetMapping("/getNoVertebratesAnimalInfo")
    public Result getNoVertebratesAnimalInfo(){
        List<Animal> animalList = animalService.selectNoVertebratesAnimalsInfo();

        setAnimalListSimpInfo(animalList);

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的脊椎动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的脊椎动物~", animalList);
        }
    }


    @ApiOperation(value = "哺乳动物-info",notes = "一级保护动物-哺乳动物的信息")
    @GetMapping("/getSuckleAnimalInfo")
    public Result getSuckleAnimalInfo(){
        List<Animal> animalList = animalService.selectSuckleAnimalsInfo();

        setAnimalListSimpInfo(animalList);

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的脊椎动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的脊椎动物~", animalList);
        }
    }


    @ApiOperation(value = "鸟类动物-info",notes = "一级保护动物-鸟类动物的信息")
    @GetMapping("/getBirdAnimalInfo")
    public Result getBirdAnimalInfo(){
        List<Animal> animalList = animalService.selectBirdAnimalsInfo();

        setAnimalListSimpInfo(animalList);

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的脊椎动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的脊椎动物~", animalList);
        }
    }


    @ApiOperation(value = "爬行动物-info",notes = "一级保护动物-爬行动物的信息")
    @GetMapping("/getCreepAnimalInfo")
    public Result getCreepAnimalInfo(){
        List<Animal> animalList = animalService.selectCreepAnimalsInfo();

        setAnimalListSimpInfo(animalList);

        if (animalList.size() == 0){
            return Result.succ(200, "当前的后台还没添加相关的脊椎动物哦~", animalList);
        }else {
            return Result.succ(200, "已查询到相关的脊椎动物~", animalList);
        }
    }

}
