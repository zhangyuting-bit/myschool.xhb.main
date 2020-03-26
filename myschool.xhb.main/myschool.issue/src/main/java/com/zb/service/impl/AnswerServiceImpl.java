package com.zb.service.impl;

import com.zb.entity.Answer;
import com.zb.mapper.AnswerMapper;
import com.zb.service.AnswerService;
import com.zb.util.IdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class AnswerServiceImpl implements AnswerService {
    @Resource
    private AnswerMapper answerMapper;

    //添加新答案
    @Override
    public Answer addAnswer(Answer answer) {
        answer.setAnswerId(IdWorker.getId());
        answerMapper.addAnswer(answer);
        return answer;
    }
}
