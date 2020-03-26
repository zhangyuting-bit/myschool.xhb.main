package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.config.RabbitConfigs;
import com.zb.entity.Select;
import com.zb.entity.Survey;
import com.zb.mapper.AnswerMapper;
import com.zb.mapper.SelectMapper;
import com.zb.mapper.SelectPicMapper;
import com.zb.mapper.SurveyMapper;
import com.zb.service.SurveyService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {
    @Resource
    private SurveyMapper surveyMapper;

    @Resource
    private SelectMapper selectMapper;

    @Resource
    private SelectPicMapper selectPicMapper;

    @Resource
    private AnswerMapper answerMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisUtil redisUtil;

    //根据班级编号查询调查通知
    @Override
    public List<Survey> getSurveyByGradeId(String gradeId) {
        return surveyMapper.getSurveyByGradeId(gradeId);
    }

    //根据调查编号查询调查信息
    @Override
    public Survey getSurveyBySurveyId(String surveyId) {
        Survey survey=null;
        String key="survey:"+surveyId;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            survey= JSON.parseObject(o.toString(), Survey.class);
        }else {
            survey=surveyMapper.getSurveyBySurveyId(surveyId);
            //根据调查编号获取全部题目
            List<Select>selects=selectMapper.getSelectBySurveyId(survey.getSurveyId());
            for (Select select:selects) {
                //根据题目编号获取所有题目图片
                select.setSelectPics(selectPicMapper.getPicBySelectId(select.getSelectId()));
                //根据题目编号获取答案
                select.setAnswers(answerMapper.getAnswerBySelectId(select.getSelectId()));
            }
            survey.setSelects(selects);
            redisUtil.set(key, JSON.toJSONString(survey),120000);
        }
        return survey;
    }

    //添加新调查
    @Override
    public Survey addSurvey(Survey survey) {
        survey.setSurveyId(IdWorker.getId());
        rabbitTemplate.convertAndSend(RabbitConfigs.myexchange,RabbitConfigs.surKey,survey);
        return survey;
    }

    //监听添加调查队列
    @RabbitListener(queues = RabbitConfigs.surQueue)
    public void getNotification(Survey survey){
        surveyMapper.addSurvey(survey);
        String key="survey:"+survey.getGradeId();
        redisUtil.set(key, JSON.toJSONString(survey),120000);
    }

    //学生端实时显示信息
    @Override
    public Survey getSurStu(String gradeId){
        String key="survey:"+gradeId;
        Object o = redisUtil.get(key);
        if (o!=null){
            Survey survey=JSON.parseObject(o.toString(),Survey.class);
            return survey;
        }
        return null;
    }

    //修改结束时间
    @Override
    public Integer updateSurEndTimeOne(String endTime, String surveyId) {
        return surveyMapper.updateSurEndTimeOne(endTime, surveyId);
    }

    //把调查状态修改为已结束
    @Override
    public Integer updateSurEndTime(String surveyId) {
        return surveyMapper.updateSurEndTime(surveyId);
    }
}
