package com.zb.service.impl;

import com.zb.entity.Notification;
import com.zb.mapper.NotificationMapper;
import com.zb.service.NotificationService;
import com.zb.util.RedisUtil;
import org.springframework.cache.annotation.Cacheable;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationServiceImpl implements NotificationService {
    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private RedisUtil redisUtil;

    //根据班级编号和通知类型编号获取全部对应通知
    @Override
    public List<Notification> getNotificationGradeId(String gradeId) {
        return notificationMapper.getNotificationGradeId(gradeId);
    }

    //根据通知编号获取通知信息
    @Override
    @Cacheable(value = "cache" ,key="#notificationId")
    public Notification getNotificationById(String notificationId) {
        Notification notification=null;
        String key="notification:"+notificationId;
        if (redisUtil.hasKey(key)){
            Map<Object, Object> hmget = redisUtil.hmget(key);
            notification.setNotificationId(hmget.get("notificationId").toString());
            notification.setTypeId(Integer.parseInt(hmget.get("typeId").toString()));
            notification.setGradeId(hmget.get("gradeId").toString());
            notification.setNotifyMessage(hmget.get("notifyMessage").toString());
            notification.setMudelId(Integer.parseInt(hmget.get("mudelId").toString()));
            notification.setNotifyTime(hmget.get("notifyTime").toString());
            notification.setTitle(hmget.get("title").toString());
            notification.setEndTime(hmget.get("endTime").toString());
        }else {
            Map<String,Object>map=new HashMap<>();
            notification=notificationMapper.getNotificationById(notificationId);
        }
        return null;
    }
}
