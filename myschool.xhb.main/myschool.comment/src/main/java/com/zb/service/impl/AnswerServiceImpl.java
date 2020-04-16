package com.zb.service.impl;

import com.zb.mapper.AnswerMapper;
import com.zb.pojo.Answer;
import com.zb.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    AnswerMapper answerMapper;

    @Override
    public int addAnswer(Answer answer) {
        return answerMapper.addAnswer(answer);
    }

    @Override
    public List<Answer> listCommentsAnswer(String commentId) {
        return answerMapper.listCommentsAnswer(commentId);
    }

    @Override
    public int deleteAnswer(String id) {
        return answerMapper.deleteAnswer(id);
    }
}
