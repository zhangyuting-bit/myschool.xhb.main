package com.zb.service.impl;

import com.zb.config.RabbitConfigs;
import com.zb.entity.Score;
import com.zb.entity.StuSubject;
import com.zb.mapper.NumberMapper;
import com.zb.mapper.ScoreMapper;
import com.zb.mapper.SubjectMapper;
import com.zb.service.ScoreService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ScoreServiceImpl implements ScoreService {
    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private NumberMapper numberMapper;

    @Resource
    private SubjectMapper subjectMapper;


    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisUtil redisUtil;

    //添加成绩信息
    @Override
    public Score addScore(Score score) {
        score.setScoreId(IdWorker.getId());
        scoreMapper.addScore(score);
        return score;
    }

//    @RabbitListener(queues = RabbitConfigs.scoQueue)
//    public void getScore(Score score){
//
//    }

    //根据班级编号获取成绩消息
    @Override
    public List<Score> getScoreListByGradeId(String gradeId) {
        return scoreMapper.getScoreListByGradeId(gradeId);
    }

    //根据消息编号修改消息
    @Override
    public Integer updateScore(Score score) {
        return scoreMapper.updateScore(score);
    }

    //根据成绩编号获取信息
    @Override
    public Score getScoreByScoreId(String scoreId) {
        Score score=scoreMapper.getScoreByScoreId(scoreId);
        score.setSubjects(subjectMapper.getSubjectByScoreId(scoreId));
        score.setNumbers(numberMapper.getNumberByGradeId(score.getGradeId()));
        return score;
    }

}
