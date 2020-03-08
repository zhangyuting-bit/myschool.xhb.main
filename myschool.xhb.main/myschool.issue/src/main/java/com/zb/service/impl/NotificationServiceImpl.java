package com.zb.service.impl;

import com.zb.entity.Notification;
import com.zb.mapper.NotificationMapper;
import com.zb.service.NotificationService;

import javax.annotation.Resource;
import java.util.List;

public class NotificationServiceImpl implements NotificationService {
    @Resource
    private NotificationMapper notificationMapper;

    //根据班级编号和通知类型编号获取全部对应通知
    @Override
    public List<Notification> getNotificationGradeId(String gradeId) {
        return null;
    }
}
