package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.vo.AnimalRescuePhoneVo;
import com.guangyou.rareanimal.pojo.vo.ForestPolicePhoneVo;

import java.util.List;

/**
 * @author xukai
 * @create 2022-11-19 9:30
 */
public interface ProtectService {

    /**
     * 根据用户定位获取野生动物救护中心电话集合
     * @return
     */
    List<AnimalRescuePhoneVo> getAnimalRescuePhoneByAddress(String userAddress);


    /**
     * 根据用户定位获取野森林公安电话集合
     * @return
     */
    List<ForestPolicePhoneVo> getForestPolicePhoneByAddress(String userAddress);
}
