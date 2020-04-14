package com.zb.mapper;

import com.zb.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {

    //新增评论
    int addComment(Comment comment);
    //查看评论
    List<Comment> listRecordOrComments(@Param("recordId") String recordId,
                                       @Param("recordType") Integer recordType);
    //删除评论
    int deleteComment(@Param("id") String id);
}
