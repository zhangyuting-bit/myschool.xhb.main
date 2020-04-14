package com.zb.service.impl;

import com.zb.mapper.CommentMapper;
import com.zb.pojo.Comment;
import com.zb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Override
    public int addComment(Comment comment) {
        return commentMapper.addComment(comment);
    }

    @Override
    public List<Comment> listSomeComments(String recordId, Integer recordType) {
        return commentMapper.listRecordOrComments(recordId,recordType);
    }

    @Override
    public int deleteComment(String id) {
        return commentMapper.deleteComment(id);
    }
}
