package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.config.RabbitConfigs;
import com.zb.dto.Dto;
import com.zb.entity.*;
import com.zb.feign.UserFeignClient;
import com.zb.mapper.NotDocumentMapper;
import com.zb.mapper.NotOneMapper;
import com.zb.mapper.NotPicMapper;
import com.zb.mapper.NotificationMapper;
import com.zb.pojo.UserInfo;
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
    private UserFeignClient userFeignClient;

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

    //根据token获取用户编号
    @Override
    public String getUserIdByToken(String token){
        UserInfo userInfo = userFeignClient.getUserInfoByToken(token);
        System.out.println(userInfo.getId());
        String userId=userInfo.getId();
        return userId;
    }

    @Cacheable(value = "cache" ,key="#notificationOne")
    public Notification getNotificationByNotId(String notificationId){
        Notification notification = null;
        String key = "notificationOne:" + notificationId;
        if (redisUtil.hasKey(key)) {
            Object o = redisUtil.get(key);
            notification = JSON.parseObject(o.toString(), Notification.class);
        } else {
            notification = notificationMapper.getNotificationById(notificationId);
            //根据teacherId获取老师信息
            ///////////////////////////
            //根据班级编号获取班级信息
            //根据通知编号查询状态为0的图片
            notification.setNotPic(notPicMapper.getPicByStatu(notification.getNotificationId()));

            redisUtil.set(key, JSON.toJSONString(notification), 120);
        }
        return notification;
    }
    //根据用户编号和通知类型编号获取全部对应通知
    @Override
    public List<Notification> getNotificationGradeId(Integer typeId, String userId) {
        List<Notification> list = new ArrayList<>();
        List<NotOne> notOnes = notOneMapper.getOneByUserId(typeId, userId);
        for (NotOne notOne : notOnes) {
            list.add(getNotificationByNotId(notOne.getFunctionId()));
        }
        return list;
    }

    //根据通知编号获取通知信息
    @Override
    @Cacheable(value = "cache" ,key="#notificationId")
    public Notification getNotification(String notificationId) {
        Notification notification = null;
        String key = "notification:" + notificationId;
        System.out.println("cache");
        if (redisUtil.hasKey(key)) {
            System.out.println("redis");
            Object o = redisUtil.get(key);
            notification = JSON.parseObject(o.toString(), Notification.class);
        } else {
            System.out.println("mysql");
            notification = notificationMapper.getNotificationById(notificationId);
            //根据teacherId获取老师信息
            ///////////////////////////

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
        redisUtil.set(key, JSON.toJSONString(notification), 60);
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
        //根据班级编号获取用户信息
        List<User>users=notificationMapper.getUserByGradeId(notification.getGradeId());
        for (User user : users) {
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

    //删除推送消息
    @Override
    public void delStuNoc(String userId, String notificationId, String gradeId) {
        String key = "notification:" + userId + gradeId;
        Object o = redisUtil.get(key);
        if (o != null) {
            Notification notification = JSON.parseObject(o.toString(), Notification.class);
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
        //根据班级编号获取用户信息
        List<User>users=notificationMapper.getUserByGradeId(notification.getGradeId());
        for (User user : users) {
            String key1 = "notification:" + user.getUserId() + user.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(notification1), 40);
            String key2 = "ok:" + user.getUserId() + gradeId;
            String ok = "";
            redisUtil.set(key2, JSON.toJSONString(ok), 20);
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

    //根据用户编号和通知编号删除通知信息
    @Override
    public Integer delNotOneByNotIdAndUserId(String userId,String notificationId){
        return notOneMapper.delNotOneByNotIdAndUserId(userId, notificationId);
    }

    //撤销通知信息
    @Override
    @Transactional
    public void returnNot(String notificationId,String gradeId){
        //根据通知编号撤销通知
        notificationMapper.delNot(notificationId);
        //根据通知编号删除个人信息
        notOneMapper.delNotOneByNotId(notificationId);
        //根据通知编号删除图片
        notPicMapper.delPicByNotId(notificationId);
        //根据通知编号删除文件
        notDocumentMapper.delDocByNotId(notificationId);

        //根据班级编号获取用户信息
        List<User>users=notificationMapper.getUserByGradeId(gradeId);
        for (User user : users) {
            String key = "notification:" + user.getUserId() + user.getGradeId();
            Object o=redisUtil.get(key);
            if (o!=null){
                Notification notification=JSON.parseObject(o.toString(),Notification.class);
                if (notificationId.equals(notification.getNotificationId())){
                    redisUtil.del(key);
                    String key1= "ok:" + user.getUserId() + user.getGradeId();
                    redisUtil.del(key1);
                }
            }
        }
        String key2="delNotification:"+gradeId;
        redisUtil.set(key2,JSON.toJSONString(notificationId),10);
        String key="notification:"+notificationId;
        if (redisUtil.hasKey(key)){
            redisUtil.del(key);
        }
    }

    //获取撤销信息
    @Override
    public String getNotDelStatus(String gradeId){
        String key="delNotification:"+gradeId;
        if (redisUtil.hasKey(key)){
            Object o=redisUtil.get(key);
            String notificationId=JSON.parseObject(o.toString(),String.class);
            return notificationId;
        }
        return null;
    }
}
