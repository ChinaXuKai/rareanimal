package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.mapper.ActivityMapper;
import com.guangyou.rareanimal.mapper.OpinionMapper;
import com.guangyou.rareanimal.mapper.OpinionReplyMapper;
import com.guangyou.rareanimal.mapper.UserMapper;
import com.guangyou.rareanimal.pojo.Opinion;
import com.guangyou.rareanimal.pojo.OpinionReply;
import com.guangyou.rareanimal.pojo.User;
import com.guangyou.rareanimal.pojo.dto.OpinionDto;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.OpinionReplyVo;
import com.guangyou.rareanimal.pojo.vo.OpinionVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;
import com.guangyou.rareanimal.service.OpinionService;
import com.guangyou.rareanimal.utils.CopyUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xukai
 * @create 2023-04-13 9:13
 */
@Service
public class OpinionServiceImpl implements OpinionService {

    @Autowired
    private OpinionMapper opinionMapper;

    @Transactional
    @Override
    public Long submitOpinion(OpinionDto opinionDto, Integer userId) {
        Opinion opinion = new Opinion();
        opinion.setOpinionContent(opinionDto.getOpinionContent());
        opinion.setUserId(userId.longValue());
        opinion.setSubmitTime(System.currentTimeMillis());
        opinion.setIsDelete(0);
        opinionMapper.insert(opinion);
        return opinion.getOpinionId();
    }

    @Transactional
    @Override
    public Integer updateOpinion(OpinionDto opinionDto, Integer userId) {
        LambdaUpdateWrapper<Opinion> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Opinion::getOpinionId, opinionDto.getOpinionId());
        //根据意见id 查询意见
        Opinion opinion = opinionMapper.getOpinionById(opinionDto.getOpinionId());
        //修改意见的内容和修改时间
        opinion.setOpinionContent(opinionDto.getOpinionContent());
        opinion.setUpdateTime(System.currentTimeMillis());
        return opinionMapper.update(opinion,updateWrapper);
    }

    @Transactional
    @Override
    public Integer deleteOpinion(Long opinionId, Integer userId) {
        return opinionMapper.deleteByOpinionId(opinionId);
    }


    @Autowired
    private CopyUtils copyUtils;

    @Override
    public PageDataVo<OpinionVo> getOpinionsByPage(PageDto pageDto, Integer userId) {
        PageDataVo<OpinionVo> pageDataVo = new PageDataVo<>();
        List<Opinion> opinionList = opinionMapper.getOpinionsByPageAndUid(userId,pageDto.getPageSize() * (pageDto.getPage() - 1), pageDto.getPageSize());
        pageDataVo.setPageData(copyUtils.opinionListCopy(opinionList));
        pageDataVo.setCurrent(pageDto.getPage());
        pageDataVo.setSize(pageDto.getPageSize());
        LambdaQueryWrapper<Opinion> queryWrapper = new LambdaQueryWrapper<Opinion>();
        queryWrapper.eq(Opinion::getUserId, userId);
        queryWrapper.eq(Opinion::getIsDelete, 0);
        int total = opinionMapper.selectCount(queryWrapper).intValue();
        pageDataVo.setTotal(total);
        int isRemainZero = total%pageDto.getPageSize();
        if (isRemainZero != 0){
            pageDataVo.setPages( (total/pageDto.getPageSize()) + 1);
        }else {
            pageDataVo.setPages( total/pageDto.getPageSize() );
        }
        return pageDataVo;
    }


    @Autowired
    private OpinionReplyMapper opinionReplyMapper;

    @Override
    public Result getReplyByOid(Long opinionId) {
        LambdaQueryWrapper<OpinionReply> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OpinionReply::getOpinionId,opinionId);
        List<OpinionReply> opinionReplyList = opinionReplyMapper.selectList(queryWrapper);

        if (opinionReplyList.isEmpty()){
            return Result.succ(200, "当前意见还没得到回复哦", null);
        }
        List<OpinionReplyVo> opinionReplyVoList = copyUtils.opinionReplyListCopy(opinionReplyList);
        return Result.succ(200, "该意见的回复内容如下", opinionReplyVoList);
    }


}
