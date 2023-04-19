package com.guangyou.rareanimal.controller;

import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.ActivityDto;
import com.guangyou.rareanimal.pojo.dto.PageDto;
import com.guangyou.rareanimal.pojo.vo.ActivityVo;
import com.guangyou.rareanimal.pojo.vo.PageDataVo;
import com.guangyou.rareanimal.service.ActivityService;
import com.guangyou.rareanimal.utils.ShiroUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.UnknownAccountException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public Result publish(@Validated @RequestBody ActivityDto activityDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("你还未登录，还不能发布活动哦");
        }

        Long startTime = sdf.parse(activityDto.getStartTime()).getTime();
        Long endTime = sdf.parse(activityDto.getEndTime()).getTime();
        Long nowTime = System.currentTimeMillis();
        //活动的 开始时间 不能大于 截止时间
        if (startTime >= endTime){
            return Result.fail(Result.FORBIDDEN, "活动的开始时间不能小于或等于截止时间",null);
        }
        //若当前时间的时间戳要 大于 活动开始时间的时间戳，即活动已经开始
        if (nowTime > startTime){
            return Result.fail(Result.FORBIDDEN, "当前时间已经超过活动开始时间", null);
        }

        Long activityId = activityService.publishActivity(userId,activityDto);
        if (activityId == 0 || activityId == null){
            return Result.fail("发布活动出现异常");
        }
        return Result.succ(200, "发布活动成功，请等待审核", activityId);
    }


    @SneakyThrows
    @ApiOperation(value = "用户修改活动")
    @PutMapping("/update")
    public Result update(@Validated @RequestBody ActivityDto activityDto){
        Integer userId = ShiroUtil.getProfile().getUserId();
        if (userId == null){
            throw new UnknownAccountException("你还未登录，还不能修改活动哦");
        }

        //活动的 开始时间 不能大于 截止时间
        Long startTime = sdf.parse(activityDto.getStartTime()).getTime();
        Long endTime = sdf.parse(activityDto.getEndTime()).getTime();
        Long nowTime = System.currentTimeMillis();
        if (startTime >= endTime){
            return Result.fail(Result.FORBIDDEN, "活动的开始时间不能小于或等于截止时间",null);
        }
        //若当前时间的时间戳要 大于 活动开始时间的时间戳，即活动已经开始
        if (nowTime >= startTime){
            return Result.fail(Result.FORBIDDEN, "当前时间已经超过活动开始时间", null);
        }

        Long activityId = activityService.updateActivity(userId,activityDto);
        if (activityId == 0 || activityId == null){
            return Result.fail("修改活动出现异常");
        }
        return Result.succ(200, "修改活动成功，请等待审核", activityId);
    }


    @ApiOperation(value = "用户参与活动")
    @PostMapping("/joinActivityByUid")
    public Result joinActivityByUid(Long activityId){
        return activityService.joinActivityByUid(activityId);
    }


    @ApiOperation(value = "用户取消报名活动")
    @DeleteMapping("/disJoinActivityByUid")
    public Result disJoinActivityByUid(Long activityId){
        return activityService.disJoinActivityByUid(activityId);
    }


    @ApiOperation(value = "用户分页查看活动")
    @GetMapping("/getActivitiesByPage")
    public Result getActivitiesByPage(PageDto pageDto){
        PageDataVo<ActivityVo> pageDataVo = activityService.getActivitiesByPage(pageDto);
        if (pageDataVo.getPageData().isEmpty()){
            return Result.succ( "当前还没有用户发布活动，快来发布属于你自己的活动吧");
        }
        return Result.succ(200, "活动分页数据如下", pageDataVo);
    }
}
