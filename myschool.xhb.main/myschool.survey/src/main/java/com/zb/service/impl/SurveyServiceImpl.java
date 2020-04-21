package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.config.RabbitConfig;
import com.zb.config.RabbitConfigs;
import com.zb.entity.*;
import com.zb.feign.ClassMassagesFeign;
import com.zb.feign.NotOneFeign;
import com.zb.feign.UserFeignClient;
import com.zb.mapper.*;
import com.zb.pojo.Class_add;
import com.zb.pojo.Class_info;
import com.zb.pojo.UserInfo;
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
    private NotOneFeign notOneFeign;

    @Resource
    private ClassMassagesFeign classMassagesFeign;

    @Resource
    private SelectMapper selectMapper;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private SelectPicMapper selectPicMapper;

    @Resource
    private AnswerMapper answerMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisUtil redisUtil;

    //根据token获取用户编号
    public String getUserIdByToken(String token){
        UserInfo userInfo = userFeignClient.getUserInfoByToken(token);
        String userId=userInfo.getId();
        return userId;
    }

    //根据班级编号获取班级信息
    @Cacheable(value = "cache" ,key="#class_number")
    public Class_add getClassInfo(String class_number){
        Class_add class_add=null;
        String key="ca:"+class_number;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            class_add = JSON.parseObject(o.toString(), Class_add.class);
        }else {
            class_add=classMassagesFeign.showclass(class_number);
            List<Class_info>infoList=classMassagesFeign.classinfo(Integer.parseInt(class_number));
            List<String>userIds=new ArrayList<>();
            for (Class_info class_info:infoList) {
                userIds.add(class_info.getUser_id().toString());
            }
            class_add.setUserIds(userIds);
            redisUtil.set(key,JSON.toJSONString(class_add), 240);
        }
        return class_add;
    }

    @Override
    public Survey getBySurveyId(String surveyId){
        Survey survey=null;
        String key="surOne:"+surveyId;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            survey= JSON.parseObject(o.toString(), Survey.class);
        }else {
            survey=surveyMapper.getSurveyBySurveyId(surveyId);
            //根据班级编号获取班级信息
            Class_add class_add=getClassInfo(survey.getGradeId());
            survey.setClass_add(class_add);
            redisUtil.set(key, JSON.toJSONString(survey),120);
        }
        return survey;
    }

    //根据用户编号获取对应调查
    @Override
    public List<Survey> getSurveyByUserId(Integer typeId,String userId) {
        List<Survey>list=new ArrayList<>();
        List<NotOne>ones=notOneFeign.getOneByUserId(typeId,userId);
        for (NotOne notOne:ones) {
            list.add(getBySurveyId(notOne.getFunctionId()));
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
            //根据班级编号获取班级信息
            Class_add class_add=getClassInfo(survey.getGradeId());
            survey.setClass_add(class_add);
            //根据调查编号获取全部题目
            List<Select>selects=selectMapper.getSelectBySurveyId(survey.getSurveyId());
            for (Select select:selects) {
                //根据题目编号获取所有题目图片
                select.setSelectPics(selectPicMapper.getPicBySelectId(select.getSelectId()));
                //根据题目编号获取答案
                select.setAnswers(answerMapper.getAnswerBySelectId(select.getSelectId()));
            }
            survey.setSelects(selects);
            redisUtil.set(key, JSON.toJSONString(survey),240);
        }
        return survey;
    }

    //添加新调查
    @Override
    public Survey addSurvey(Survey survey) {
        survey.setSurveyId(IdWorker.getId());
        surveyMapper.addSurvey(survey);
        return survey;
    }

    //推送调查消息
    @Override
    public void sendSurvey(String surveyId){
        Survey survey=surveyMapper.getSurveyBySurveyId(surveyId);
        //根据班级编号获取班级信息
        Class_add class_add=getClassInfo(survey.getGradeId());
        survey.setClass_add(class_add);
        //根据班级编号获取用户信息
        List<String>userIds=getClassInfo(survey.getGradeId()).getUserIds();
        for (String userId : userIds) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(survey.getSurveyId());
            notOne.setUserId(userId);
            notOne.setTypeId(survey.getTypeId());
            notOne.setCreateTime(survey.getStartTime());
            notOneFeign.addNotOne(notOne);
            String key1 = "survey:" + userId + survey.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(survey), 40);
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
            }
        }
    }

    //撤销调查信息
    @Override
    @Transactional
    public void returnSurvey(String surveyId){
        Survey survey1=surveyMapper.getSurveyBySurveyId(surveyId);
        if (survey1==null){
            return;
        }
        //根据调查编号删除调查
        surveyMapper.delSurveyBySurveyId(surveyId);
        //根据调查编号删除个人信息
        notOneFeign.delNotOneByNotIdAndUserId(surveyId,survey1.getTypeId());
        //根据调查编号查询题目信息
        List<Select>list=selectMapper.getSelectBySurveyId(surveyId);
        for (Select select:list) {
            //根据题目编号删除图片信息
            selectPicMapper.delPicBySelectId(select.getSelectId());
            //根据题目编号删除答案信息
            answerMapper.delAnswerBySelId(select.getSelectId());
        }
        //根据调查编号删除题目信息
        selectMapper.delSelectBySurId(surveyId);
        //根据班级编号获取用户信息
        List<String>userIds=getClassInfo(survey1.getGradeId()).getUserIds();
        for (String userId : userIds) {
            String key = "survey:" + userId + survey1.getGradeId();
            if (redisUtil.get(key)!=null){
                Survey survey=JSON.parseObject(redisUtil.get(key).toString(),Survey.class);
                if (surveyId.equals(survey.getSurveyId())){
                    redisUtil.del(key);
                }
            }
        }
        String key1 = "surOne:" + surveyId;
        if (redisUtil.hasKey(key1)){
            redisUtil.del(key1);
        }
        String key2="delSur:"+survey1.getGradeId();
        redisUtil.set(key2,JSON.toJSONString(surveyId),10);
        String key="survey:"+surveyId;
        if (redisUtil.hasKey(key)){
            redisUtil.del(key);
        }
    }

    //获取撤销信息
    @Override
    public String getSurDelStatus(String gradeId){
        String key="delSur:"+gradeId;
        if (redisUtil.hasKey(key)){
            Object o=redisUtil.get(key);
            String scoreId=JSON.parseObject(o.toString(),String.class);
            return scoreId;
        }
        return null;
    }

    //根据班级编号获取调查信息
    @Override
    public List<Survey> getSurveyByGrade(String gradeId) {
        return surveyMapper.getSurveyByGrade(gradeId);
    }

}
