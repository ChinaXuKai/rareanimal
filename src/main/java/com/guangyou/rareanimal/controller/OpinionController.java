package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.OpinionDto;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.OpinionVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;
import com.guangyou.rareanimal.service.OpinionService;
import com.guangyou.rareanimal.utils.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xukai
 * @create 2023-04-13 8:54
 */
@Slf4j
@RestController
@RequestMapping("/opinion")
@Api(tags = "用户意见控制器（都需要传jwt）")
public class OpinionController {

    @Autowired
    private OpinionService opinionService;

    @ApiOperation(value = "用户提交意见")
    @PostMapping("/submit")
    public Result submit(@RequestBody OpinionDto opinionDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            return Result.fail(Result.FORBIDDEN,"当前未登录，还不能发表意见哦",null);
        }

        Long opinionId = opinionService.submitOpinion(opinionDto,userId);
        if (opinionId == null){
            return Result.fail("提交意见出现异常");
        }
        return Result.succ(200, "提交意见成功", opinionId);
    }


    @ApiOperation(value = "用户修改意见")
    @PutMapping("/update")
    public Result update(OpinionDto opinionDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            return Result.fail(Result.FORBIDDEN,"当前未登录，还不能发表意见哦",null);
        }

        Integer updateResult = opinionService.updateOpinion(opinionDto,userId);
        if (updateResult == null){
            return Result.fail("修改意见出现异常");
        }
        return Result.succ(200, "修改意见成功", opinionDto.getOpinionId());
    }


    @ApiOperation(value = "用户删除意见")
    @DeleteMapping("/delete")
    public Result delete(Long opinionId){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            return Result.fail(Result.FORBIDDEN,"当前未登录，还不能发表意见哦",null);
        }

        Integer deleteResult = opinionService.deleteOpinion(opinionId,userId);
        if (deleteResult == null){
            return Result.fail("删除意见出现异常");
        }
        return Result.succ(200, "删除意见成功", opinionId);
    }


}
