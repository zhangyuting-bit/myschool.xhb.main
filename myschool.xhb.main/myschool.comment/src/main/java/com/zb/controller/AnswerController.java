package com.zb.controller;

import com.zb.pojo.Answer;
import com.zb.service.AnswerService;
import com.zb.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnswerController {

    @Autowired(required = false)
    AnswerService answerService;

    @RequestMapping(value = "/addAnswer")
    public int addAnswer(@RequestBody Answer answer){
        answer.setId(IdWorker.getId());
        return answerService.addAnswer(answer);
    }

    @RequestMapping(value = "/listCommentsAnswer")
    public List<Answer> listCommentsAnswer(@RequestParam("commentId") String commentId){
        return answerService.listCommentsAnswer(commentId);
    }


    @RequestMapping(value = "/deleteAnswer")
    public int deleteAnswer(@RequestParam("id") String id){
        return answerService.deleteAnswer(id);
    }

}
