package com.guangyou.rareanimal.service;


import com.guangyou.rareanimal.pojo.User;
import com.guangyou.rareanimal.pojo.vo.UserVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author xukai
 * @create 2022-11-02 22:50
 */
public interface UserService {

    //注册用户
    int addUser(String userName,String userAccount, String userPwd, String userAvatar, Long accountCreateTime);

    //根据用户名密码查询用户
    UserVo selectUserByAccountAndPwd(String userAccount, String userPwd);

    //根据用户Id修改用户信息
    int updateUserInfo(Integer userId, String userName);

}
