package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.config.RabbitConfigs;
import com.zb.entity.Notification;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class NotificationServiceImpl implements NotificationService {
    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisUtil redisUtil;

    //根据班级编号和通知类型编号获取全部对应通知
    @Override
    public List<Notification> getNotificationGradeId(Integer typeId,String gradeId) {
        return notificationMapper.getNotificationGradeId(typeId,gradeId);
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
            notification.setNotifyTime(hmget.get("notifyTime").toString());
            notification.setTitle(hmget.get("title").toString());
            notification.setEndTime(hmget.get("endTime").toString());
        }else {
            Map<String,Object>map=new HashMap<>();
            notification=notificationMapper.getNotificationById(notificationId);
            map.put("notificationId",notification.getNotificationId());
            map.put("typeId",notification.getTypeId());
            map.put("gradeId",notification.getGradeId());
            map.put("notifyMessage",notification.getNotifyMessage());
            map.put("notifyTime",notification.getNotifyTime());
            map.put("title",notification.getTitle());
            map.put("endTime",notification.getEndTime());
            redisUtil.hmset(key,map);
        }
        return notification;
    }

    //添加新的通知信息
    @Override
    @Transactional
    public Notification addNotification(Notification notification) {
        notification.setNotificationId(IdWorker.getId());
        rabbitTemplate.convertAndSend(RabbitConfigs.myexchange,RabbitConfigs.nocKey,notification);
        return notification;
    }

    @RabbitListener(queues = RabbitConfigs.nocQueue)
    public void getNotification(Notification notification){
        notificationMapper.addNotification(notification);
        String key="notification:"+notification.getGradeId();
        redisUtil.set(key, JSON.toJSONString(notification));
    }

    //学生端实时显示信息
    @Override
    public Notification getNocStu(Integer typeId,String gradeId){
        String key="notification:"+gradeId;
        Object o = redisUtil.get(key);
        if (o!=null){
            Notification notification=JSON.parseObject(o.toString(),Notification.class);
            if (typeId==notification.getTypeId()||typeId==0){
                return notification;
            }
        }
        return null;
    }

}
