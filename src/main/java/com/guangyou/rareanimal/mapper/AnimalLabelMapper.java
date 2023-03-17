package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.AnimalLabel;
import com.guangyou.rareanimal.pojo.vo.AnimalLabelVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-03-06 8:43
 */
@Repository
public interface AnimalLabelMapper extends BaseMapper<AnimalLabel> {

    AnimalLabelVo selectAnimalLabelById(Long animalId);
}
