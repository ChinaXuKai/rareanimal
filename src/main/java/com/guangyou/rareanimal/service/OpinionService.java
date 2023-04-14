package com.guangyou.rareanimal.service;

import com.guangyou.rareanimal.pojo.dto.OpinionDto;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.OpinionVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;

/**
 * @author xukai
 * @create 2023-04-13 9:13
 */
public interface OpinionService {
    /**
     * 用户提交意见
     * @param opinionDto 意见参数
     * @param userId 用户id
     * @return 意见主键id
     */
    Long submitOpinion(OpinionDto opinionDto, Integer userId);

    /**
     * 用户修改意见
     * @param opinionDto 意见参数
     * @param userId 用户id
     * @return 修改结果
     */
    Integer updateOpinion(OpinionDto opinionDto, Integer userId);

    /**
     * 用户删除意见
     * @param opinionId 意见主键id
     * @param userId 用户id
     * @return 删除结果
     */
    Integer deleteOpinion(Long opinionId, Integer userId);

    /**
     * 用户查看 意见分页数据集
     * @param pageDto 分页条件
     * @param userId 用户id
     * @return 意见分页数据集
     */
    PageDataVo<OpinionVo> getOpinionsByPage(PageDto pageDto, Integer userId);
}
