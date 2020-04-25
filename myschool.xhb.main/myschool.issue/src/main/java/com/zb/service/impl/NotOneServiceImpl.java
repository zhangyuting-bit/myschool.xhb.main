package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.zb.config.RabbitConfig;
import com.zb.entity.*;
import com.zb.feign.*;
import com.zb.mapper.NotOneMapper;
import com.zb.pojo.*;
import com.zb.service.NotOneService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import com.zb.vo.UserVo;
import org.apache.activemq.broker.scheduler.Job;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotOneServiceImpl implements NotOneService {
    @Resource
    private NotOneMapper notOneMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private ClassMassagesFeign classMassagesFeign;

    @Resource
    private NotificationCollectFeign notificationCollectFeign;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private SurveyCollectFeign surveyCollectFeign;

    @Resource
    private ScoreCollectFeign scoreCollectFeign;

    @Resource
    private RedisUtil redisUtil;

    //根据token获取用户编号
    public String getUserIdByToken(String token){
        //根据token获取用户编号
        UserInfo userInfo = userFeignClient.getUserInfoByToken(token);
        String userId=userInfo.getId();
        return userId;
    }

    //根据用户编号获取用户所在所有班级
    @Override
    @Cacheable(value = "cache" ,key="#userId")
    public UserVo getUserGrade(String userId){
        //String userId=getUserIdByToken(token);
        UserVo userVo=new UserVo();
        String key="grade:"+userId;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            userVo = JSON.parseObject(o.toString(), UserVo.class);
        }else {
            List<Class_info>infoList=classMassagesFeign.showclassid(Integer.parseInt(userId));
            List<String>gradeList=new ArrayList<>();
            for (Class_info class_info:infoList) {
                gradeList.add(class_info.getClass_number().toString());
            }
            userVo.setGradeList(gradeList);
            redisUtil.set(key,JSON.toJSONString(userVo), 240);
        }
        return userVo;
    }

    //添加个人通知消息
    @RabbitListener(queues = RabbitConfig.notQueue)
    public void addNotOne(Notification notification){
        for (String userId : notification.getUserId()) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(notification.getNotificationId());
            notOne.setUserId(userId);
            notOne.setTypeId(notification.getTypeId());
            notOne.setCreateTime(notification.getNotifyTime());
            notOneMapper.addOne(notOne);
            String key1 = "not:" + userId + notification.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(notification), 40);
        }
    }

    //添加个人调查消息
    @RabbitListener(queues = RabbitConfig.surQueue)
    public void addSurOne(Survey survey){
        for (String userId : survey.getUserIds()) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(survey.getSurveyId());
            notOne.setUserId(userId);
            notOne.setTypeId(survey.getTypeId());
            notOne.setCreateTime(survey.getStartTime());
            notOneMapper.addOne(notOne);
            String key1 = "survey:" + userId + survey.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(survey), 40);
        }
    }

    //添加个人成绩消息
    @RabbitListener(queues = RabbitConfig.scoQueue)
    public void addScoOne(Score score){
        for (String userId : score.getUserIds()) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(score.getScoreId());
            notOne.setUserId(userId);
            notOne.setTypeId(score.getTypeId());
            notOne.setCreateTime(score.getCreateTime());
            notOneMapper.addOne(notOne);
            String key1 = "score:" + userId + score.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(score), 40);
        }
    }

    //根据用户编号获取用户所有信息
    @Override
    public List<NotOne> getOneAll(String userId) {
        List<NotOne>ones=new ArrayList<>();
        List<NotOne>list=notOneMapper.getOneByUserId(0,userId);
        for (NotOne notOne:list) {
            if (notOne.getTypeId()==1||notOne.getTypeId()==2||notOne.getTypeId()==3||notOne.getTypeId()==4||notOne.getTypeId()==5){
                notOne.setNotification(notificationCollectFeign.getNotificationByNotId(notOne.getFunctionId()));
                ones.add(notOne);
            }else if (notOne.getTypeId()==6){
                notOne.setSurvey(surveyCollectFeign.getBySurveyId(notOne.getFunctionId()));
                ones.add(notOne);
            }else if (notOne.getTypeId()==7){
                notOne.setScore(scoreCollectFeign.getByScoreId(notOne.getFunctionId()));
                ones.add(notOne);
            }
        }
        return ones;
    }

    //根据用户编号和通知类型编号获取全部对应通知
    @Override
    public List<NotOne> getOneByUserId(Integer typeId, String userId) {
        return notOneMapper.getOneByUserId(typeId, userId);
    }

    //根据信息编号和信息类型撤销信息
    @Override
    public Integer delNotOneByNotIdAndUserId(String functionId,Integer typeId) {
        return notOneMapper.delNotOneByNotIdAndUserId(functionId,typeId);
    }

    //根据消息编号和用户编号和类型编号删除信息
    @Override
    public Integer delNotOneByNotId(String functionId,String userId,Integer typeId) {
        return notOneMapper.delNotOneByNotId(userId,functionId,typeId);
    }

    //加入班级后添加之前此班级发的信息
    @Transactional
    @RabbitListener(queues = RabbitConfig.classinfo)
    public void intoClass(ClassTask classTask){
        Class_Jobinfo jobinfo=JSON.parseObject(classTask.getRequest_body(),Class_Jobinfo.class);
        if (notOneMapper.getOneByUser(jobinfo.getNumber().toString())!=null){
            return;
        }
        //根据班级编号查询此班级所有之前发送的信息
        List<Notification>notifications=notificationCollectFeign.getNotificationByGrade(jobinfo.getClass_number().toString());
        List<Survey>surveys=surveyCollectFeign.getSurveyByGrade(jobinfo.getClass_number().toString());
        List<Score>scores=scoreCollectFeign.getScoreListByGradeId(jobinfo.getClass_number().toString());
        for (Notification notification:notifications) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(notification.getNotificationId());
            notOne.setUserId(jobinfo.getNumber().toString());
            notOne.setTypeId(notification.getTypeId());
            notOne.setCreateTime(notification.getNotifyTime());
            notOneMapper.addOne(notOne);
        }

        for (Survey survey : surveys) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(survey.getSurveyId());
            notOne.setUserId(jobinfo.getNumber().toString());
            notOne.setTypeId(survey.getTypeId());
            notOne.setCreateTime(survey.getStartTime());
            notOneMapper.addOne(notOne);
        }

        for (Score score : scores) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(score.getScoreId());
            notOne.setUserId(jobinfo.getNumber().toString());
            notOne.setTypeId(score.getTypeId());
            notOne.setCreateTime(score.getCreateTime());
            notOneMapper.addOne(notOne);
        }

        //查询redis是否存在相应用户信息
        String key="user:"+jobinfo.getNumber().toString();
        if (redisUtil.hasKey(key)) {
            Object o = redisUtil.get(key);
            UserInfo userInfo = JSON.parseObject(o.toString(), UserInfo.class);
            List<String>gradeIds=userInfo.getGradeIds();
            gradeIds.add(jobinfo.getClass_number().toString());
            userInfo.setGradeIds(gradeIds);
            redisUtil.set(key,JSON.toJSONString(userInfo), 120);
        }

        //查询redis是否存在相应班级信息
        String key1="ca:"+jobinfo.getClass_number().toString();
        if (redisUtil.hasKey(key1)){
            Object o = redisUtil.get(key1);
            Class_add class_add = JSON.parseObject(o.toString(), Class_add.class);
            List<String>userIds=class_add.getUserIds();
            userIds.add(jobinfo.getNumber().toString());
            class_add.setUserIds(userIds);
            redisUtil.set(key1,JSON.toJSONString(class_add), 120);
        }
        classMassagesFeign.updateTaskCount(classTask.getId());
    }

    //退班之后删除此班级发的消息
    public void delClass(String gradeId,String userId){
        //根据班级编号和用户编号删除notOne表里的个人信息
        notOneMapper.delNotOneByGradeIdAndUserId(gradeId,userId);

        //查询redis是否存在相应用户信息
        String key="user:"+userId;
        if (redisUtil.hasKey(key)) {
            Object o = redisUtil.get(key);
            UserInfo userInfo = JSON.parseObject(o.toString(), UserInfo.class);
            List<String>gradeIds=userInfo.getGradeIds();
            gradeIds.remove(gradeId);
            userInfo.setGradeIds(gradeIds);
            redisUtil.set(key,JSON.toJSONString(userInfo), 120);
        }

        //查询redis是否存在相应班级信息
        String key1="ca:"+gradeId;
        if (redisUtil.hasKey(key1)){
            Object o = redisUtil.get(key1);
            Class_add class_add = JSON.parseObject(o.toString(), Class_add.class);
            List<String>userIds=class_add.getUserIds();
            userIds.remove(userId);
            class_add.setUserIds(userIds);
            redisUtil.set(key1,JSON.toJSONString(class_add), 120);
        }
    }
}
