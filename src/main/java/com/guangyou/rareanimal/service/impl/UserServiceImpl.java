package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guangyou.rareanimal.mapper.ArticleMapper;
import com.guangyou.rareanimal.mapper.UserCarerMapper;
import com.guangyou.rareanimal.mapper.UserMapper;
import com.guangyou.rareanimal.pojo.Article;
import com.guangyou.rareanimal.pojo.User;
import com.guangyou.rareanimal.pojo.UserCarer;
import com.guangyou.rareanimal.pojo.vo.UserVo;
import com.guangyou.rareanimal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xukai
 * @create 2022-11-02 22:51
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册用户
     * @param userAccount 用户账号
     * @param userPwd 密码
     * @return 注册用户受影响的行数
     */
    @Override
    public int addUser(String userName,String userAccount, String userPwd, String userAvatar, Long accountCreateTime) {
        //1.先根据用户账号查询用户
        List<User> userList = userMapper.getUsersByAccount(userAccount);

        //2.判断是否能添加用户
        if (userList.size() != 0){
            /*
             * 用户数不为0，则继续判断该用户的账号与已有的用户账号是否仅有大小写区别
             *  仅有大小写区别的话，也可以添加新用户；除开大小写还有其他区别的话，不可添加新用户
             */
            for (User user : userList){
                String existUserAccount = user.getUserAccount();
                if (!userAccount.equals(existUserAccount)){
                    return userMapper.insertUserByAccountAndPwd(userName,userAccount, userPwd, userAvatar, accountCreateTime);
                }else {
                    return -1;
                }
            }
            return -1;
        }else {//用户数为0，则说明用户账号可用，可添加新用户
            int result = userMapper.insertUserByAccountAndPwd(userName,userAccount, userPwd, userAvatar, accountCreateTime);
            return result;
        }
    }

    /**
     * 根据用户名和密码查询用户
     * @param userAccount 用户账号
     * @param userPwd 密码
     * @return 返回查询到的用户
     */
    @Override
    public UserVo selectUserByAccountAndPwd(String userAccount, String userPwd) {
        //1.先根据用户账号和密码查询用户集合
        List<User> userList = userMapper.getUsersByAccountAndPwd(userAccount, userPwd);

        //2.判断用户集合长度是否为0
        if (userList.size() == 0){//长度为0，返回null
            return null;
        }else{                    //用户集合不为0
            for (User user : userList){   //循环判断用户账号和密码一致，返回该用户
                if (userAccount.equals(user.getUserAccount()) && userPwd.equals(user.getUserPwd()) ){
                    return copyUserFiled(user, true,true, true, true);
                }
            }
        }
        return null;
    }

    @Autowired
    private UserCarerMapper userCarerMapper;
    @Autowired
    private ArticleMapper articleMapper;
    //获取用户的创建时间、粉丝数、关注数、文章篇数，boolean判断是否要为其赋值
    private UserVo copyUserFiled(User user,boolean isCreateTime,boolean isFanCounts,boolean isCareCounts, boolean isArticleCounts){
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        if (isCreateTime){
            //将 user 里的 Long类型createTime 转换为 String类型的createTime
            userVo.setCreateTime(new DateTime(user.getCreateTime()).toString("yyyy-MM-dd HH:mm:ss"));
        }
        if (isFanCounts){
            //在 t_user_carer 表中，按照 carerId 查询 userId
            List<Long> fansIdList = userCarerMapper.selectCarersIdByUserId(userVo.getUserId().longValue());
            userVo.setFanCounts(fansIdList.size());
        }
        if (isCareCounts){
            //在 t_user_carer 表中，按照 userId 查询 carerId
            LambdaQueryWrapper<UserCarer> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(UserCarer::getUserId, userVo.getUserId());
            List<UserCarer> userCarers = userCarerMapper.selectList(queryWrapper);
            userVo.setCareCounts(userCarers.size());
        }
        if (isArticleCounts){
            //在 t_article 表中，按照 author_account 查询 id个数
            LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Article::getAuthorAccount, userVo.getUserAccount());
            Long articleCounts = articleMapper.selectCount(queryWrapper);
            userVo.setArticleCounts(articleCounts.intValue());
        }
        return userVo;
    }


    @Override
    public int updateUserInfo(Integer userId, String userName, String imgUrl) {
        return userMapper.updateUserInfoById(userId,userName,imgUrl);
    }


}
