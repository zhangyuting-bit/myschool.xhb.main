package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.StuComment;
import com.zb.service.StuCommentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StuCommentController {
    @Resource
    private StuCommentService stuCommentService;

    //添加个人评论
    @RequestMapping("/addComment")
    public Dto<Integer> addComment(StuComment stuComment) {
        return DtoUtil.returnSuccess("ok",stuCommentService.addComment(stuComment));
    }

}
