package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.RecommendUser;
import com.guangyou.rareanimal.pojo.dto.UserDto;
import com.guangyou.rareanimal.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2022-11-02 22:55
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据账号密码获取用户
     * @param userAccount 用户名
     * @param userPwd 密码
     * @return 查询到的用户
     */
    List<User> getUsersByAccountAndPwd(String userAccount, String userPwd);

    /**
     * 根据账号获取用户
     * @param userAccount 用户名
     * @return 查询到的用户
     */
    List<User> getUsersByAccount(String userAccount);


    /**
     * 添加新用户
     * @param userName 新用户的用户名
     * @param userAccount 新用户的用户账号
     * @param userPwd 新用户的密码
     */
    int insertUserByAccountAndPwd(String userName,String userAccount, String userPwd, String userAvatar, Long accountCreateTime);

    /**
     * 根据用户Id修改用户昵称
     * @param userName
     * @param userId
     * @return
     */
    int updateUserNameById(String userName,Integer userId);

    /**
     * 根据用户id修改用户头像
     * @param imageUrl 保存在图片服务器上的图片所在地址
     * @param userId 当前用户id
     * @return
     */
    int updateUserAvatarById(String imageUrl, Integer userId);

    /**
     * 根据用户id修改用户信息
     * @param userId 用户id
     * @param userName 用户昵称
     * @return 影响的行数
     */
    int updateUserInfoById(Integer userId, String userName, String imgUrl);

    /**
     * 根据主键id 查询用户
     * @param toUid
     * @return
     */
    User getUsersByUid(Long toUid);

//    /**
//     * 根据 用户id 修改 用户头像
//     * @param userId 用户id
//     * @param imageUrl 用户头像url地址
//     */
//    void updateAvatarById(int userId, String imageUrl);
}
