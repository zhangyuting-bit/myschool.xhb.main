package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.config.RabbitConfig;
import com.zb.entity.JobTask;
import com.zb.entity.NotOne;
import com.zb.entity.NotPic;
import com.zb.entity.Notification;
import com.zb.feign.ClassMassagesFeign;
import com.zb.feign.NotOneFeign;
import com.zb.feign.UserFeignClient;
import com.zb.mapper.JobTaskMapper;
import com.zb.mapper.NotDocumentMapper;
import com.zb.mapper.NotPicMapper;
import com.zb.mapper.NotificationMapper;
import com.zb.pojo.Class_add;
import com.zb.pojo.Class_info;
import com.zb.pojo.UserInfo;
import com.zb.service.NotificationService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Resource
    private JobTaskMapper jobTaskMapper;

    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private ClassMassagesFeign classMassagesFeign;

    @Resource
    private NotOneFeign notOneFeign;

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
    public String getUserIdByToken(String token) {
        //根据token获取用户编号
        UserInfo userInfo = userFeignClient.getUserInfoByToken(token);
        String userId = userInfo.getId();
        return userId;
    }

    //根据班级编号获取班级信息
    @Cacheable(value = "cache", key = "#class_number")
    public Class_add getClassInfo(String class_number) {
        Class_add class_add = null;
        //存储班级信息的key值
        String key = "ca:" + class_number;
        if (redisUtil.hasKey(key)) {
            Object o = redisUtil.get(key);
            class_add = JSON.parseObject(o.toString(), Class_add.class);
        } else {
            class_add = classMassagesFeign.showclass(class_number);
            List<Class_info> infoList = classMassagesFeign.classinfo(Integer.parseInt(class_number));
            List<String> userIds = new ArrayList<>();
            for (Class_info class_info : infoList) {
                userIds.add(class_info.getUser_id().toString());
            }
            class_add.setUserIds(userIds);
            redisUtil.set(key, JSON.toJSONString(class_add), 240);
        }
        return class_add;
    }

    //根据通知编号获取通知列表单个通知信息
    public Notification getNotificationByNotId(String notificationId) {
        Notification notification = null;
        String key = "notOne:" + notificationId;
        if (redisUtil.hasKey(key)) {
            Object o = redisUtil.get(key);
            notification = JSON.parseObject(o.toString(), Notification.class);
        } else {
            notification = notificationMapper.getNotificationById(notificationId);
            //根据班级编号获取班级信息
            Class_add class_add = getClassInfo(notification.getGradeId());
            notification.setClass_add(class_add);
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
        List<NotOne> notOnes = notOneFeign.getOneByUserId(typeId, userId);
        for (NotOne notOne : notOnes) {
            list.add(getNotificationByNotId(notOne.getFunctionId()));
        }
        return list;
    }


    //根据通知编号获取通知信息
    @Override
    @Cacheable(value = "cache", key = "#notificationId")
    public Notification getNotification(String notificationId) {
        Notification notification = null;
        String key = "not:" + notificationId;
        if (redisUtil.hasKey(key)) {
            Object o = redisUtil.get(key);
            notification = JSON.parseObject(o.toString(), Notification.class);
        } else {
            notification = notificationMapper.getNotificationById(notificationId);
            //根据班级编号获取班级信息
            Class_add class_add = getClassInfo(notification.getGradeId());
            notification.setClass_add(class_add);
            //根据通知编号查询图片
            notification.setNotPics(notPicMapper.getPicByFId(notification.getNotificationId()));
            //根据通知编号查询附件
            notification.setDocuments(notDocumentMapper.getDocumentByNId(notification.getNotificationId()));
            redisUtil.set(key, JSON.toJSONString(notification), 240);
        }
        return notification;
    }

    //添加新的通知信息
    @Override
    public Notification addNotification(Notification notification) {
        notification.setNotificationId(IdWorker.getId());
        notificationMapper.addNotification(notification);
        if (notification.getTypeId() == 2) {
            if (!notification.getTaskTime().equals("不提醒")) {
                JobTask jobTask = new JobTask();
                jobTask.setId(IdWorker.getId());
                jobTask.setGradeId(notification.getGradeId());
                jobTask.setNotificationId(notification.getNotificationId());
                jobTask.setTaskTime(notification.getTaskTime());
                jobTaskMapper.addJobTask(jobTask);
            }
        }
        return notification;
    }


    //学生端实时显示信息
    @Override
    public Notification getNocStu(Integer typeId, String userId, String gradeId) {
        String key = "not:" + userId + gradeId;
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
        String key = "not:" + userId + gradeId;
        Object o = redisUtil.get(key);
        if (o != null) {
            Notification notification = JSON.parseObject(o.toString(), Notification.class);
            if (notificationId.equals(notification.getNotificationId())) {
                redisUtil.del(key);
            }
        }
    }

    //添加推送状态
    @Override
    public void addStatus(String gradeId, String notificationId) {
        Notification notification = notificationMapper.getNotificationById(notificationId);
        //根据班级编号获取班级信息
        Class_add class_add = getClassInfo(notification.getGradeId());
        notification.setClass_add(class_add);
        //根据通知编号查询状态为0的图片
        NotPic picByStatu = notPicMapper.getPicByStatu(notification.getNotificationId());
        notification.setNotPic(picByStatu);
        //根据班级编号获取用户信息
        List<String> userIds = getClassInfo(notification.getGradeId()).getUserIds();
        notification.setUserId(userIds);
        rabbitTemplate.convertAndSend(RabbitConfig.myexchange,RabbitConfig.notKey,notification);
    }

    //撤销通知信息
    @Override
    @Transactional
    public void returnNot(String notificationId) {
        Notification notification1 = notificationMapper.getNotificationById(notificationId);
        if (notification1 == null) {
            return;
        }
        //根据通知编号撤销通知
        notificationMapper.delNot(notificationId);
        //根据通知编号删除个人信息
        notOneFeign.delNotOneByNotIdAndUserId(notificationId, notification1.getTypeId());
        //根据通知编号删除图片
        notPicMapper.delPicByNotId(notificationId);
        //根据通知编号删除文件
        notDocumentMapper.delDocByNotId(notificationId);

        //根据班级编号获取用户信息
        List<String> userIds = getClassInfo(notification1.getGradeId()).getUserIds();
        for (String userId : userIds) {
            String key = "not:" + userId + notification1.getGradeId();
            Object o = redisUtil.get(key);
            if (o != null) {
                Notification notification = JSON.parseObject(o.toString(), Notification.class);
                if (notificationId.equals(notification.getNotificationId())) {
                    redisUtil.del(key);
                }
            }
        }
        String key1 = "notOne:" + notificationId;
        if (redisUtil.hasKey(key1)) {
            redisUtil.del(key1);
        }
        String key2 = "delNot:" + notification1.getGradeId();
        redisUtil.set(key2, JSON.toJSONString(notificationId), 10);
        String key = "not:" + notificationId;
        if (redisUtil.hasKey(key)) {
            redisUtil.del(key);
        }
    }

    //获取撤销信息
    @Override
    public String getNotDelStatus(String gradeId) {
        String key = "delNot:" + gradeId;
        if (redisUtil.hasKey(key)) {
            Object o = redisUtil.get(key);
            String notificationId = JSON.parseObject(o.toString(), String.class);
            return notificationId;
        }
        return null;
    }


    //根据班级编号获取全部通知信息
    @Override
    public List<Notification> getNotificationByGrade(String gradeId) {
        return notificationMapper.getNotificationByGrade(gradeId);
    }
}
