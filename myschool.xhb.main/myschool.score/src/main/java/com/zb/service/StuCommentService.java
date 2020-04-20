package com.zb.service;

import com.zb.entity.StuComment;

public interface StuCommentService {
    ///添加学生评论
    Integer addComment(StuComment stuComment);

    //根据成绩编号和学号编号获取评论
    StuComment getCommentByScoreAndNumberId(String scoreId,String numberId);
}
