package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.Answer;
import com.zb.service.AnswerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AnswerController {
     @Resource
    private AnswerService answerService;

    //添加新答案
    @RequestMapping("/addAnswer")
    public Dto<Answer> addAnswer(Answer answer) {
        return DtoUtil.returnSuccess("ok",answerService.addAnswer(answer));
    }
}
