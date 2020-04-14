package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.config.RabbitConfig;
import com.zb.entity.*;
import com.zb.feign.NotificationCollectFeign;
import com.zb.feign.ScoreCollectFeign;
import com.zb.feign.SurveyCollectFeign;
import com.zb.mapper.NotOneMapper;
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
    private NotificationCollectFeign notificationCollectFeign;

    @Resource
    private SurveyCollectFeign surveyCollectFeign;

    @Resource
    private ScoreCollectFeign scoreCollectFeign;
    @Resource
    private RedisUtil redisUtil;

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
        List<User>users=notOneMapper.getUserByGradeId(notification.getGradeId());
        for (User user : users) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(notification.getNotificationId());
            notOne.setUserId(user.getUserId());
            notOne.setTypeId(notification.getTypeId());
            notOneMapper.addOne(notOne);
            String key1 = "notification:" + user.getUserId() + user.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(notification), 40);
            String key2 = "ok:" + user.getUserId() + notification.getGradeId();
            String ok = "";
            redisUtil.set(key2, JSON.toJSONString(ok), 40);
        }
    }

    //监听添加调查队列
    @RabbitListener(queues = RabbitConfig.surQueue)
    public void addSurvey(Survey survey) {
        //根据班级编号获取用户信息
        List<User>users=notOneMapper.getUserByGradeId(survey.getGradeId());
        for (User user : users) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(survey.getSurveyId());
            notOne.setUserId(user.getUserId());
            notOne.setTypeId(survey.getTypeId());
            notOneMapper.addOne(notOne);
            String key1 = "survey:" + user.getUserId() + user.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(survey), 40);
            String key2 = "ok:" + user.getUserId() + survey.getGradeId();
            String ok = "";
            redisUtil.set(key2, JSON.toJSONString(ok), 40);
        }
    }

    //监听添加成绩队列
    @RabbitListener(queues = RabbitConfig.scoQueue)
    public void addScore(Score score) {
        //根据班级编号获取用户信息
        List<User>users=notOneMapper.getUserByGradeId(score.getGradeId());
        for (User user : users) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(score.getScoreId());
            notOne.setUserId(user.getUserId());
            notOne.setTypeId(score.getTypeId());
            notOneMapper.addOne(notOne);
            String key1 = "survey:" + user.getUserId() + user.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(score), 40);
            String key2 = "ok:" + user.getUserId() + score.getGradeId();
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
