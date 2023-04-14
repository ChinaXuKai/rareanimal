package com.guangyou.rareanimal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.guangyou.rareanimal.mapper.CommentsMapper;
import com.guangyou.rareanimal.mapper.UserMapper;
import com.guangyou.rareanimal.pojo.Comment;
import com.guangyou.rareanimal.pojo.dto.CommentDto;
import com.guangyou.rareanimal.pojo.vo.AuthorVo;
import com.guangyou.rareanimal.pojo.vo.CommentsVo;
import com.guangyou.rareanimal.pojo.User;
import com.guangyou.rareanimal.service.CommentsService;
import org.apache.shiro.authc.UnknownAccountException;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xukai
 * @create 2023-01-03 17:00
 */
@Service
public class CommentsServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 1、根据文章id 查询评论列表集合
     * 2、根据作者账号 查询作者信息
     * 3、判断 若level = 1，则要去查询有没有子评论
     *          若有，则根据to_uid字段进行查询
     */
    @Override
    public List<CommentsVo> findCommentsByArticleId(Long articleId) {
        List<Comment> comments = commentsMapper.selectFirstFloorCommentsByArticleId(articleId);
        return copyList(comments);
    }


    @Override
    public CommentsVo publishComment(CommentDto commentDto,String userAccount) {
        Long parentId = commentDto.getParentId();
        Long toUserId = commentDto.getToUserId();

        Comment comment = new Comment();
        //设置articleId、content、createDate、authorAccount、parentId
        comment.setContent(commentDto.getContent());
        comment.setCreateDate(System.currentTimeMillis());
        comment.setArticleId(commentDto.getArticleId());
        comment.setAuthorAccount(userAccount);
        comment.setParentId(parentId == null ? 0 : parentId);
        comment.setIsDelete(0);
        //设置level、toUid
        if (parentId == null || parentId == 0){     //没有父评论，说明是第一层，也没有给谁评论
            comment.setLevel(1);
            comment.setToUid(Integer.toUnsignedLong(0));
        }else {         //有父评论，则要查询其父评论是第几层，设置其为 父评论楼层+1，设置给父评论的用户评论
            LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Comment::getId, parentId);
            Comment parentComment = commentsMapper.selectOne(queryWrapper);
            comment.setLevel(parentComment.getLevel() + 1);

            comment.setToUid(toUserId == null ? 0 : toUserId);
        }
        commentsMapper.insert(comment);
        return copy(comment);
    }


    /**
     * 删除评论（开启事务）：
     * 情况1：仅删除该评论
     * 情况2：若是 第一层评论 被删除，则 该评论的所有子评论 被删除，
     *        若是 非第一层评论 被删除，则 仅删除该评论
     * @param commentId 被删除的评论id
     * @return
     */
    @Transactional
    @Override
    public int deleteCommentById(Long commentId) {
        int deleteResult = 0;
        Comment comment = commentsMapper.selectById(commentId);
        //若是 第一层评论 被删除，则 该评论的所有子评论 被删除
        if (comment.getLevel() == 1) {
            //找出该评论的 所有子评论 以及 子评论的所有子评论
            //根据当前评论id 获取所有子评论 以及 子评论的所有子评论 集合
            List<Comment> sonComments = getCommentsByParentId(commentId, comment.getLevel());
            List<Long> sonCommentIds = new ArrayList<>();
            for (Comment sonComment : sonComments){
                //将子评论id 添加到 子评论id集合
                sonCommentIds.add(sonComment.getId());
            }
            //循环 子评论id集合，根据 子评论id 删除子评论
            for (Long sonCommentId : sonCommentIds){
                commentsMapper.deleteById(sonCommentId);
                deleteResult++;
            }
            return deleteResult;
        }else{  //若是 非第一层评论 被删除，则 仅删除该评论
            deleteResult = commentsMapper.deleteById(commentId);
            return deleteResult;
        }
    }


    private List<CommentsVo> copyList(List<Comment> comments){
        List<CommentsVo> commentsVos = new ArrayList<>();
        for (Comment comment : comments){
            commentsVos.add(copy(comment));
        }
        return commentsVos;
    }

    private CommentsVo copy(Comment comment){
        CommentsVo commentsVo = new CommentsVo();
        BeanUtils.copyProperties(comment, commentsVo);
        //CommentsVo的id、content、level字段可以通过BeanUtils来赋值，
        // 而author、childrenComments、createDate、toUser需要通过查询来赋值
        //作者信息
        String authorAccount = comment.getAuthorAccount();
        List<User> users = userMapper.getUsersByAccount(authorAccount);
        if (users.size() != 0){
            for (User user : users){
                String existUserAccount = user.getUserAccount();
                //若账号大小写一致则说明为该用户，将评论的作者设置为该用户
                if (existUserAccount.equals(comment.getAuthorAccount())){
                    AuthorVo author = new AuthorVo();
                    BeanUtils.copyProperties(user, author);
                    commentsVo.setAuthor(author);
                }
            }
        }else {
            throw new UnknownAccountException("未查找到该用户");
        }
        //子评论
        Long id = comment.getId();
        List<Comment> childrenComments = getCommentsByParentId(id,comment.getLevel());
        List<CommentsVo> childrenCommentsVo = copyList(childrenComments);
        commentsVo.setChildrenComments(childrenCommentsVo);
        //发表评论时间
        commentsVo.setCreateDate(new DateTime(comment.getCreateDate()).toString("yyyy-MM-dd HH:mm:ss"));
        //给谁评论
        if (comment.getLevel() > 1){
            Long toUid = comment.getToUid();
            User toUser = userMapper.getUsersByUid(toUid);
            AuthorVo toAuthor = new AuthorVo();
            BeanUtils.copyProperties(toUser, toAuthor);
            commentsVo.setToUser(toAuthor);
        }
        return commentsVo;
    }

    private List<Comment> getCommentsByParentId(Long id,Integer commentLevel) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getParentId, id);
        queryWrapper.eq(Comment::getLevel, commentLevel + 1);
        return commentsMapper.selectList(queryWrapper);
    }
}
