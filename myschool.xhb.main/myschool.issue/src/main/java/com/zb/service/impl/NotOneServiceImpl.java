package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.config.RabbitConfig;
import com.zb.entity.*;
import com.zb.feign.*;
import com.zb.mapper.NotOneMapper;
import com.zb.pojo.Class_add;
import com.zb.pojo.Class_info;
import com.zb.pojo.UserInfo;
import com.zb.service.NotOneService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

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

    //根据班级编号获取班级信息
    public Class_add getClassInfo(String class_number){
        Class_add class_add=null;
        String key="class_add:"+class_number;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            class_add = JSON.parseObject(o.toString(), Class_add.class);
        }else {
            class_add=classMassagesFeign.showclass(class_number);
            List<Class_info>infoList=classMassagesFeign.classinfo(Integer.parseInt(class_number));
            List<String>userIds=new ArrayList<>();
            for (Class_info class_info:infoList){
                userIds.add(class_info.getUser_id().toString());
            }
            class_add.setUserIds(userIds);
            redisUtil.set(key,JSON.toJSONString(class_add), 120);
        }
        return class_add;
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

    //监听添加通知队列
    @RabbitListener(queues = RabbitConfig.nocQueue)
    public void addNotification(Notification notification) {
        //根据班级编号获取用户信息
        List<String>userIds=getClassInfo(notification.getGradeId()).getUserIds();
        for (String userId : userIds) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(notification.getNotificationId());
            notOne.setUserId(userId);
            notOne.setTypeId(notification.getTypeId());
            notOneMapper.addOne(notOne);
            String key1 = "notification:" + userId + notification.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(notification), 40);
            String key2 = "ok:" + userId + notification.getGradeId();
            String ok = "";
            redisUtil.set(key2, JSON.toJSONString(ok), 40);
        }
    }

    //监听添加调查队列
    @RabbitListener(queues = RabbitConfig.surQueue)
    public void addSurvey(Survey survey) {
        //根据班级编号获取用户信息
        List<String>userIds=getClassInfo(survey.getGradeId()).getUserIds();
        for (String userId : userIds) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(survey.getSurveyId());
            notOne.setUserId(userId);
            notOne.setTypeId(survey.getTypeId());
            notOneMapper.addOne(notOne);
            String key1 = "survey:" + userId + survey.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(survey), 40);
            String key2 = "ok:" + userId + survey.getGradeId();
            String ok = "";
            redisUtil.set(key2, JSON.toJSONString(ok), 40);
        }
    }

    //监听添加成绩队列
    @RabbitListener(queues = RabbitConfig.scoQueue)
    public void addScore(Score score) {
        //根据班级编号获取用户信息
        List<String>userIds=getClassInfo(score.getGradeId()).getUserIds();
        for (String userId : userIds) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(score.getScoreId());
            notOne.setUserId(userId);
            notOne.setTypeId(score.getTypeId());
            notOneMapper.addOne(notOne);
            String key1 = "survey:" + userId + score.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(score), 40);
            String key2 = "ok:" + userId + score.getGradeId();
            String ok = "";
            redisUtil.set(key2, JSON.toJSONString(ok), 40);
        }
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

}
