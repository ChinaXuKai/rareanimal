package com.guangyou.rareanimal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guangyou.rareanimal.pojo.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xukai
 * @create 2023-01-03 17:26
 */
@Repository
public interface CommentsMapper extends BaseMapper<Comment> {

    /**
     * 通过文章id 查询该文章的第一层评论
     * @param articleId
     * @return
     */
    List<Comment> selectFirstFloorCommentsByArticleId(Long articleId);

    /**
     * 通过评论id 获取对应评论的 support_counts字段值
     * @param commentId
     */
    Integer selectSupportCountsByCommentId(Long commentId);

    /**
     * 通过评论id 增加对应评论的 support_counts字段值
     * @param commentId
     */
    int increaseSupportCounts(Long commentId,int beforeSupportCounts);

    /**
     * 通过评论id 减小对应评论的 support_counts字段值
     * @param commentId
     */
    int decreaseSupportCounts(Long commentId, int beforeSupportCounts);

//    /**
//     * 根据 评论id 进行逻辑删除
//     * @param commentId 评论id
//     * @return
//     */
//    int deleteByCommentId(Long commentId);
}
