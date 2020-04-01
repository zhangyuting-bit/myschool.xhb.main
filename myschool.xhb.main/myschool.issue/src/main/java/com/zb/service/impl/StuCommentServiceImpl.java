package com.zb.service.impl;

import com.zb.entity.StuComment;
import com.zb.mapper.StuCommentMapper;
import com.zb.service.StuCommentService;
import com.zb.util.IdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StuCommentServiceImpl implements StuCommentService {
    @Resource
    private StuCommentMapper stuCommentMapper;

    //添加个人评论
    @Override
    public Integer addComment(StuComment stuComment) {
        stuComment.setCommentId(IdWorker.getId());
        return stuCommentMapper.addComment(stuComment);
    }


}
