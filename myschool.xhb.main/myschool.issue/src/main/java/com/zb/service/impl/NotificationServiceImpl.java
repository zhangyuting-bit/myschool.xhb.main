package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.config.RabbitConfigs;
import com.zb.entity.*;
import com.zb.mapper.NotDocumentMapper;
import com.zb.mapper.NotOneMapper;
import com.zb.mapper.NotPicMapper;
import com.zb.mapper.NotificationMapper;
import com.zb.service.NotificationService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private NotOneMapper notOneMapper;

    @Resource
    private NotDocumentMapper notDocumentMapper;

    @Resource
    private NotPicMapper notPicMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisUtil redisUtil;

    //根据班级编号和通知类型编号获取全部对应通知
    @Override
    public List<Notification> getNotificationGradeId(Integer typeId, String userId) {
        List<Notification> list = new ArrayList<>();
        List<NotOne> notOnes = notOneMapper.getOneByUserId(typeId, userId);
        for (NotOne notOne : notOnes) {
            list.add(getNotification(notOne.getFunctionId()));
        }
        return list;
    }

    //根据通知编号获取通知信息
    @Override
    @Cacheable(value = "cache", key = "#notificationId")
    public Notification getNotification(String notificationId) {
        Notification notification = null;
        String key = "notification:" + notificationId;
        if (redisUtil.hasKey(key)) {
            Object o = redisUtil.get(key);
            notification = JSON.parseObject(o.toString(), Notification.class);
        } else {
            notification = notificationMapper.getNotificationById(notificationId);
            //根据teacherId获取老师信息
            ///////////////////////////

            //根据通知编号查询状态为0的图片
            notification.setNotPic(notPicMapper.getPicByStatu(notification.getNotificationId()));
            //根据通知编号查询图片
            notification.setNotPics(notPicMapper.getPicByFId(notification.getNotificationId()));
            //根据通知编号查询附件
            notification.setDocuments(notDocumentMapper.getDocumentByNId(notification.getNotificationId()));
            redisUtil.set(key, JSON.toJSONString(notification), 120);
        }
        return notification;
    }

    //根据通知编号获取通知信息
    public Notification getNotificationById(Notification notification, String teacherId) {
        String key = "notification:" + teacherId;
        redisUtil.set(key, JSON.toJSONString(notification), 120);
        return notification;
    }

    //添加新的通知信息
    @Override
    public Notification addNotification(Notification notification) {
        notification.setNotificationId(IdWorker.getId());
        rabbitTemplate.convertAndSend(RabbitConfigs.myexchange, RabbitConfigs.nocKey, notification);
        return notification;
    }

    //监听添加通知队列
    @RabbitListener(queues = RabbitConfigs.nocQueue)
    public void getNotification(Notification notification) {
        notificationMapper.addNotification(notification);
        //根据teacherId获取老师信息
        ///////////////////////////
        for (User user : notificationMapper.getUserByGradeId(notification.getGradeId())) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(notification.getNotificationId());
            notOne.setUserId(user.getUserId());
            notOne.setTypeId(notification.getTypeId());
            notOneMapper.addOne(notOne);
            String key = "notification:" + user.getUserId() + user.getGradeId();
            redisUtil.set(key, JSON.toJSONString(getNotificationById(notification, notification.getTeacherId())), 120);
        }
    }

    //学生端实时显示信息
    @Override
    public Notification getNocStu(Integer typeId, String userId, String gradeId) {
        String key = "notification:" + userId + gradeId;
        Object o = redisUtil.get(key);
        if (o != null) {
            Notification notification = JSON.parseObject(o.toString(), Notification.class);
            if (typeId == notification.getTypeId() || typeId == 0) {
                return notification;
            }
        }
        return null;
    }

    //修改结束时间
    @Override
    public Integer updateEndTimeOne(String endTime, String notificationId) {
        return notificationMapper.updateEndTimeOne(endTime, notificationId);
    }

    //把通知状态修改为已结束
    @Override
    public Integer updateEndTime(String notificationId) {
        return notificationMapper.updateEndTime(notificationId);
    }

    //删除推送消息
    @Override
    public void delStuNoc(String userId, String notificationId, String gradeId) {
        String key = "notification:" + userId + gradeId;
        Object o = redisUtil.get(key);
        if (o != null) {
            Notification notification = JSON.parseObject(o.toString(), Notification.class);
            System.out.println(notification.getNotificationId());
            if (notificationId.equals(notification.getNotificationId())) {
                redisUtil.del(key);
                redisUtil.del("ok:" + userId + gradeId);
            }
        }
    }

    //添加推送状态
    @Override
    public void addStatus(String gradeId, String teacherId) {
        String key = "notification:" + teacherId;
        Object o = redisUtil.get(key);
        Notification notification = JSON.parseObject(o.toString(), Notification.class);
        Notification notification1=notificationMapper.getNotificationById(notification.getNotificationId());
        //根据通知编号查询状态为0的图片
        notification1.setNotPic(notPicMapper.getPicByStatu(notification.getNotificationId()));
        for (User user : notificationMapper.getUserByGradeId(gradeId)) {
            String key1 = "notification:" + user.getUserId() + user.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(notification1), 40);
            String key2 = "ok:" + user.getUserId() + gradeId;
            String ok = "";
            redisUtil.set(key2, JSON.toJSONString(ok), 40);
        }
        redisUtil.del(key);
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
}
