package com.zb.service;

import com.zb.pojo.Answer;

import java.util.List;

public interface AnswerService {

    //新增回答
    int addAnswer(Answer answer);
    //查看评论回答
    List<Answer> listCommentsAnswer(String commentId);
    //删除评论回答
    int deleteAnswer(String id);

}
