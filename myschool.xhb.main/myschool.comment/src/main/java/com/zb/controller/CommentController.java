package com.zb.controller;

import com.zb.pojo.Comment;
import com.zb.service.AnswerService;
import com.zb.service.CommentService;
import com.zb.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {

    @Autowired
    CommentService commentService;

    @Autowired
    AnswerService answerService;

    @RequestMapping(value = "/addcomment")
    public int addcomment(@RequestBody Comment comment){
        comment.setId(IdWorker.getId());
        return commentService.addComment(comment);
    }

    @RequestMapping(value = "/somcomments")
    public List<Comment> somecomments(@RequestParam("recordId") String recordId,
                                      @RequestParam("recordType") Integer recordType){
        return commentService.listSomeComments(recordId,recordType);
    }

    @RequestMapping(value = "/deletecomment")
    public int deletecomment(@RequestParam("id") String id){
        answerService.deleteAnswer(id);
        return commentService.deleteComment(id);
    }

}
