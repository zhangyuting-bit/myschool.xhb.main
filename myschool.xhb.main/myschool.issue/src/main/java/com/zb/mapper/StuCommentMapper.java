package com.zb.mapper;

import com.zb.entity.StuComment;

public interface StuCommentMapper {
    //添加学生评论
    Integer addComment(StuComment stuComment);
}
