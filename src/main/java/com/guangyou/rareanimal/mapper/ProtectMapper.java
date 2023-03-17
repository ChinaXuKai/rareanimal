package com.guangyou.rareanimal.mapper;

import com.guangyou.rareanimal.pojo.vo.AnimalRescuePhoneVo;
import com.guangyou.rareanimal.pojo.vo.ForestPolicePhoneVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2022-11-19 9:27
 */
@Repository
public interface ProtectMapper {

    //根据用户定位查询野生动物救助电话集合
    List<AnimalRescuePhoneVo> selectAnimalRescuePhoneByAddress(String userAddress);

    //根据用户定位查询全国森林公安电话集合
    List<ForestPolicePhoneVo> selectForestPolicePhoneByAddress(String userAddress);
}
