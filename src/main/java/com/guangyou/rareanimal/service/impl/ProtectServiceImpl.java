package com.guangyou.rareanimal.service.impl;

import com.guangyou.rareanimal.mapper.ProtectMapper;
import com.guangyou.rareanimal.pojo.vo.AnimalRescuePhoneVo;
import com.guangyou.rareanimal.pojo.vo.ForestPolicePhoneVo;
import com.guangyou.rareanimal.service.ProtectService;
import org.omg.PortableServer.POA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xukai
 * @create 2022-11-19 9:30
 */
@Service
public class ProtectServiceImpl implements ProtectService {

    @Autowired
    private ProtectMapper protectMapper;


    @Override
    public List<AnimalRescuePhoneVo> getAnimalRescuePhoneByAddress(String userAddress) {
        List<AnimalRescuePhoneVo> phoneList = null;
        if (userAddress == null){       //用户没登录或没定位时
            phoneList = protectMapper.selectAnimalRescuePhoneByAddress(null);
        }else {                         //用户已登录时
            phoneList = protectMapper.selectAnimalRescuePhoneByAddress(userAddress);
        }
        return phoneList;
    }


    @Override
    public List<ForestPolicePhoneVo> getForestPolicePhoneByAddress(String userAddress) {
        List<ForestPolicePhoneVo> phoneList = null;
        if (userAddress == null){       //用户没登录或没定位时
            phoneList = protectMapper.selectForestPolicePhoneByAddress(null);
        }else {                         //用户已登录时
            phoneList = protectMapper.selectForestPolicePhoneByAddress(userAddress);
        }
        return phoneList;
    }
}
