package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.config.RabbitConfigs;
import com.zb.entity.*;
import com.zb.mapper.*;
import com.zb.service.SurveyService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {
    @Resource
    private SurveyMapper surveyMapper;

    @Resource
    private SurveyOneMapper surveyOneMapper;

    @Resource
    private NotificationMapper notificationMapper;

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

    //根据用户编号获取对应调查
    @Override
    public List<Survey> getSurveyByUserId(String userId) {
        List<Survey>list=new ArrayList<>();
        for (SurveyOne surveyOne:surveyOneMapper.getSurveyOneByUserId(userId)) {
            list.add(getSurveyBySurveyId(surveyOne.getSurveyId()));
        }
        return list;
    }

    //根据调查编号查询调查信息
    @Override
    @Cacheable(value = "cache", key = "#surveyId")
    public Survey getSurveyBySurveyId(String surveyId) {
        Survey survey=null;
        String key="survey:"+surveyId;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            survey= JSON.parseObject(o.toString(), Survey.class);
        }else {
            survey=surveyMapper.getSurveyBySurveyId(surveyId);
            //根据teacherId获取老师信息
            ///////////////////////////

            //根据调查编号获取全部题目
            List<Select>selects=selectMapper.getSelectBySurveyId(survey.getSurveyId());
            for (Select select:selects) {
                //根据题目编号获取所有题目图片
                select.setSelectPics(selectPicMapper.getPicBySelectId(select.getSelectId()));
                //根据题目编号获取答案
                select.setAnswers(answerMapper.getAnswerBySelectId(select.getSelectId()));
            }
            survey.setSelects(selects);
            redisUtil.set(key, JSON.toJSONString(survey),120);
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
        //根据teacherId获取老师信息
        ///////////////////////////
        //根据班级编号获取用户信息
        List<User>users=notificationMapper.getUserByGradeId(survey.getGradeId());
        for (User user : users) {
            SurveyOne surveyOne=new SurveyOne();
            surveyOne.setOneId(IdWorker.getId());
            surveyOne.setUserId(user.getUserId());
            surveyOne.setSurveyId(survey.getSurveyId());
            surveyOneMapper.addSurveyOne(surveyOne);
            String key = "survey:" + user.getUserId() + user.getGradeId();
            redisUtil.set(key, JSON.toJSONString(survey), 120);
            String key1= "ok:" + user.getUserId() + user.getGradeId();
            String ok = "";
            redisUtil.set(key1, JSON.toJSONString(ok), 40);
        }
    }

    //学生端实时显示信息
    @Override
    public Survey getSurStu( String userId,String gradeId){
        String key="survey:"+userId+gradeId;
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

    //删除推送消息
    @Override
    public void delStuSur(String userId, String surveyId, String gradeId) {
        String key = "survey:" + userId + gradeId;
        Object o = redisUtil.get(key);
        if (o != null) {
            Survey survey = JSON.parseObject(o.toString(), Survey.class);
            if (surveyId.equals(survey.getSurveyId())) {
                redisUtil.del(key);
                redisUtil.del("ok:" + userId + gradeId);
            }
        }
    }

    //获取推送状态
    @Override
    public Integer getStatus(String userId, String gradeId) {
        String key = "ok:" + userId + gradeId;
        if (redisUtil.hasKey(key)) {
            return 1;
        }
        return null;
    }

    //根据用户编号和调查编号删除成绩信息
    @Override
    public Integer delSurvey(String userId,String surveyId){
        return surveyOneMapper.delSurveyByUserId(userId, surveyId);
    }

    //撤销调查信息
    @Override
    @Transactional
    public void returnSurvey(String surveyId,String gradeId){
        //根据调查编号删除调查
        surveyMapper.delSurveyBySurveyId(surveyId);
        //根据调查编号删除个人信息
        surveyOneMapper.delSurveyOneBySurId(surveyId);
        //根据调查编号查询题目信息
        List<Select>list=selectMapper.getSelectBySurveyId(surveyId);
        for (Select select:list) {
            //根据题目编号删除图片信息
            selectPicMapper.delPicBySelectId(select.getSelectId());
            //根据题目编号删除答案信息
            answerMapper.delAnswerBySelId(select.getSelectId());
        }
        //根据班级编号获取用户信息
        List<User>users=notificationMapper.getUserByGradeId(gradeId);
        for (User user : users) {
            String key = "survey:" + user.getUserId() + user.getGradeId();
            redisUtil.del(key);
            String key1= "ok:" + user.getUserId() + user.getGradeId();
            redisUtil.del(key1);
            String key2="delSurvey:"+user.getUserId()+gradeId;
            redisUtil.set(key2,JSON.toJSONString(surveyId),120);
        }
        String key="survey:"+surveyId;
        if (redisUtil.hasKey(key)){
            redisUtil.del(key);
        }
    }

    //获取撤销信息
    @Override
    public String getSurDelStatus(String userId,String gradeId){
        String key="delSurvey:"+userId+gradeId;
        if (redisUtil.hasKey(key)){
            Object o=redisUtil.get(key);
            String scoreId=JSON.toJSONString(o.toString());
            return scoreId;
        }
        return null;
    }

    //删除撤销信息
    @Override
    public void delStatus(String userId,String gradeId){
        String key="delSurvey:"+userId+gradeId;
        redisUtil.del(key);
    }


}
