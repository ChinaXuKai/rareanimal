package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.vo.AnimalRescuePhoneVo;
import com.guangyou.rareanimal.pojo.vo.ForestPolicePhoneVo;
import com.guangyou.rareanimal.service.ProtectService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author xukai
 * @create 2022-11-19 9:02
 */
@Slf4j
@RestController
@RequestMapping("/protect")
@Api(tags = "保护热线相关接口")
public class ProtectController {

    @Autowired
    private ProtectService protectService;


    /**
     * 若用户没登录 或 用户没定位，则默认返回全国的 野生动物救护中心电话/森林公安电话
     * 若用户有定位，则返回当地的 野生动物救护中心电话/森林公安电话
     */
    @ApiOperation(value = "全国野生动物救护中心电话",notes = "全国野生动物救护中心电话集合（用户有登录则传jwt，没登录可不传）")
    @GetMapping("/getAnimalRescuePhoneList")
    public Result getAnimalRescuePhoneList(String address){
        List<AnimalRescuePhoneVo> phoneList;
        if (address == null){        //用户没选定位置
            //1、获取全国动物救助电话集合
            phoneList = protectService.getAnimalRescuePhoneByAddress(null);
        }else {                      //用户有选定位置
            //1、根据用户选定位置来获取当地救助电话集合
            phoneList = protectService.getAnimalRescuePhoneByAddress(address);
        }
        if (phoneList.size() == 0){
            return Result.fail(Result.FORBIDDEN,"该位置没有动物救助电话",null);
        }
        //将该集合封装为结果集返回
        return Result.succ(200, "当遇到需救助的动物不知道如何处理时，可以联系这些电话", phoneList);
    }


    @ApiOperation(value = "全国森林公安电话",notes = "全国森林公安电话集合（用户有登录则传jwt，没登录可不传）")
    @GetMapping("/getForestPolicePhoneList")
    public Result getForestPolicePhoneList(String address){
        List<ForestPolicePhoneVo> phoneList;
        if (address == null){       //用户没选定位置
            //1、获取全国森林公安电话
            phoneList = protectService.getForestPolicePhoneByAddress(null);
        }else {                     //用户有选定位置
            //1、根据用户选定位置来获取当地森林公安电话集合
            phoneList = protectService.getForestPolicePhoneByAddress(address);
        }

        if (phoneList.size() == 0){
            return Result.fail(Result.FORBIDDEN,"该位置没有林公安电话",null);
        }
        //将该集合封装为结果集返回
        return Result.succ(200,"当您发现有捕鸟、贩鸟等违法犯罪行为时，可以联系当地的森林公安",phoneList);
    }
}
