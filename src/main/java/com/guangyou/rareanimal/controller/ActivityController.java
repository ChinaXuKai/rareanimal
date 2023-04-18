package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.ActivityDto;
import com.guangyou.rareanimal.service.ActivityService;
import com.guangyou.rareanimal.utils.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;

/**
 * @author xukai
 * @create 2023-04-17 13:45
 */
@Slf4j
@Api(tags = "活动相关接口（都需要传jwt）")
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @SneakyThrows
    @ApiOperation(value = "用户发布活动")
    @PostMapping("/publish")
    public Result publish(ActivityDto activityDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("你还未登录，还不能发布活动哦");
        }

        //若当前时间的时间戳要 大于 活动开始时间的时间戳，即活动已经开始
        if (System.currentTimeMillis() > sdf.parse(activityDto.getStartTime()).getTime()){
            return Result.fail(Result.FORBIDDEN, "当前时间已经超过活动开始时间", null);
        }

        int activityId = activityService.publishActivity(userId,activityDto);
        if (activityId == 0){
            return Result.fail("发表活动出现异常");
        }
        return Result.succ(200, "发表活动成功，请等待审核", activityId);
    }


    @SneakyThrows
    @ApiOperation(value = "用户修改活动")
    @PutMapping("/update")
    public Result update(ActivityDto activityDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("你还未登录，还不能发布活动哦");
        }

        //若当前时间的时间戳要 大于 活动开始时间的时间戳，即活动已经开始
        if (System.currentTimeMillis() > sdf.parse(activityDto.getStartTime()).getTime()){
            return Result.fail(Result.FORBIDDEN, "当前时间已经超过活动开始时间", null);
        }

        Long activityId = activityService.updateActivity(userId,activityDto);
        if (activityId == 0 || activityId == null){
            return Result.fail("修改活动出现异常");
        }
        return Result.succ(200, "修改活动成功，请等待审核", activityId);
    }


}
