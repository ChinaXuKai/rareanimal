package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.UserCarer;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-22 12:52
 */
@Repository
public interface UserCarerMapper extends BaseMapper<UserCarer> {

    /**
     * 根据 userId 获取其对应的 carerId 集合
     * @param userId
     * @return
     */
    List<Long> selectCarersIdByUserId(Long userId);

    /**
     * 根据 userId 获取其对应的 carerId 集合（分页）
     * @param userId
     * @return
     */
    List<Long> selectCarersIdPageByUserId(Long userId, Integer initialDataLocation, Integer pageSize);

    /**
     * 根据 userId 获取对应的 careId 的个数
     * @param
     */
    Integer getCarerCountByUserId(Long userId);

    /**
     * 通过 用户id和文章作者账号 查询 用户是否是 文章作者的粉丝
     * @param userId 用户id
     * @param authorAccount 文章作者账号
     * @return
     */
    UserCarer selectCarerById(Long userId, String authorAccount);

    /**
     * 根据 当前用户id 和 别的用户author的id 去查询是否有记录
     * @param userId 当前用户
     * @param authorId 别的用户
     * @return 记录数
     */
    int selectCountById(Long userId, Long authorId);
}
