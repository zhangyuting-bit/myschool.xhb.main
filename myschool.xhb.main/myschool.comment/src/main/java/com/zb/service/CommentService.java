package com.zb.service;

import com.zb.pojo.Comment;

import java.util.List;

public interface CommentService {

    int addComment(Comment comment);
    List<Comment> listSomeComments(String recordId,
                                   Integer recordType);
    int deleteComment(String id);

}
