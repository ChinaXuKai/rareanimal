package com.guangyou.rareanimal.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.pojo.dto.UserDto;
import com.guangyou.rareanimal.pojo.User;
import com.guangyou.rareanimal.pojo.vo.UserVo;
import com.guangyou.rareanimal.service.UserService;
import com.guangyou.rareanimal.utils.JwtUtil;
import com.guangyou.rareanimal.utils.RedisUtil;
import com.guangyou.rareanimal.utils.ShiroUtil;
import io.swagger.annotations.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UnknownAccountException;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author xukai
 * @create 2022-11-02 16:41
 */
@Slf4j
@RestController
@RequestMapping("/user")
@Api(tags = "用户信息相关接口")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${userVo.defaultValue.userAvatar}")
    private String userAvatar;


    @ApiOperation(value = "注册用户",notes = "注册用户（不需要传jwt）")
    @PostMapping("/registerUser")
    public Result registerUser(@RequestBody @Validated UserDto userDto){
        //获取注册参数：用户账号和用户密码，设置用户昵称默认值
        String userAccount = userDto.getUserAccount();
        String userPwd = userDto.getUserPwd();
        String userName = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5);

        //调用userService.addUser 添加用户，返回受影响的行数
        int result = userService.addUser(userName,userAccount,userPwd,userAvatar,System.currentTimeMillis());
        if (result == -1){//受影响的行数为-1时，则说明注册失败，返回失败的结果集
            return Result.fail(Result.FORBIDDEN,"注册失败，用户名和密码已存在，请重新注册",null);
        }else {//受影响的行数不为-1时，则说明注册成功，返回成功的结果集
            UserVo user = new UserVo();
            user.setUserName(userName);
            user.setUserAccount(userAccount);
            user.setUserPwd(userPwd);
            user.setCreateTime(new DateTime(System.currentTimeMillis()).toString("yyyy-MM-dd HH:mm:ss"));
            user.setUserAvatar(userAvatar);
            user.setUserAddress(null);
            return Result.succ(200, "注册成功，可以登录了~", user);
        }
    }


    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @SneakyThrows
    @ApiOperation(value = "用户登录，生成jwt",notes = "用户登录，有效期为7天（不需要传jwt）")
    @PostMapping("/loginUser")
    public Result loginUser(@RequestBody @Validated UserDto userDto, HttpServletResponse response){
        //调用userService层查询是否有该用户
        UserVo user = userService.selectUserByAccountAndPwd(userDto.getUserAccount(),userDto.getUserPwd());
        if (user == null){
            throw new UnknownAccountException("用户不存在");
        }
        // 生成 jwt 信息，并将其封装到responseHeader中
        String jwt = jwtUtil.generateToken(user.getUserId());
        response.setHeader("token", jwt);
        response.setHeader("Access-control-Expose-Headers", "token");
        // 将 JWT 信息存储至 redis 的 库0 中，并设置过期时间
        String jsonUser = new ObjectMapper().writeValueAsString(user);
        redisUtil.select(0);
        redisUtil.set(jwt, jsonUser);           //jwt:jsonUser
        redisUtil.expire(jwt, 7, TimeUnit.DAYS);
        log.info("jwtUtil.generateTokenById={}",jwt);
        log.info("jsonUser={}",jsonUser);
        return Result.succ(200, "登录成功", user);
    }


    @ApiOperation(value = "退出登录，并删除redis中的token",notes = "退出登录，并删除redis中的token（需要传jwt）")
    @PostMapping("/logoutUser")
    public Result logoutUser(HttpServletRequest request){
        SecurityUtils.getSubject().logout();
        //获取Jwt信息，将其从redis中删除
        String jwt = request.getHeader("Authorization");
        redisUtil.delete(jwt);
        return Result.succ("退出登录成功");
    }


    @ApiOperation(value = "更新jwt",notes = "更新jwt，删除redis中的token，重新生成jwt，保存入redis，有效期为7天（需要传jwt）")
    @PostMapping("/update_jwt")
    public Result updateJwt(HttpServletRequest request,HttpServletResponse response){
        //要生成新的jwt就要先获取当前用户Id
        Integer userId = ShiroUtil.getProfile().getUserId();
        //由jwtUtil生成新的 jwt 信息
        String newJwt = jwtUtil.generateToken(userId);
        //将新的jwt存入响应头中
        response.setHeader("token", newJwt);
        response.setHeader("Access-control-Expose-Headers", "token");
        //从请求头中获取旧的jwt
        String oldJwt = request.getHeader("Authorization");
        // 获取 Redis 中对应的JWT信息
        String jsonUser = redisUtil.get(oldJwt);
        // 设置旧的 jwt 两分钟后 失效
        redisUtil.expire(oldJwt, 120, TimeUnit.SECONDS);
//        redisUtil.delete(oldJwt);
        // 把新的 JWT 信息存到redis中
        redisUtil.set(newJwt, jsonUser);
        redisUtil.expire(newJwt, 7, TimeUnit.DAYS);
        SecurityUtils.getSubject().logout();
        User user = JSON.parseObject(jsonUser, User.class);
        log.info("JSON.parseObject(jsonUser, UserVo.class)={}", user);
        return Result.succ(200, "更新jwt成功", user);
    }


    @ApiOperation(value = "修改用户信息",notes = "修改用户信息：昵称，头像")
    @PutMapping("updateUserInfo")
    public Result updateUserInfo(String userName,String imgUrl){
        //获取当前用户id 和 原先的账号
        Integer userId = ShiroUtil.getProfile().getUserId();
        String userAccount = ShiroUtil.getProfile().getUserAccount();
        String userPwd = ShiroUtil.getProfile().getUserPwd();
        //修改用户信息
            //1、先判断用户是否存在
        if(userId != null) {     //说明用户存在
                //2、修改用户信息
            int result = userService.updateUserInfo(userId,userName,imgUrl);
            if (result == 1) {
                UserVo user = userService.selectUserByAccountAndPwd(userAccount, userPwd);
                return Result.succ(200, "修改信息成功", user);
            }
            return Result.fail(Result.BAD_REQUEST,"修改信息失败",null);
        }
        //用户不存在
        throw new UnknownAccountException("当前用户不存在，无法修改信息");
    }

}
