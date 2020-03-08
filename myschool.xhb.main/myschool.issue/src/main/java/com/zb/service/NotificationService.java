package com.zb.service;

import com.zb.entity.Notification;

import java.util.List;

public interface NotificationService {
    //根据班级编号和通知类型编号获取全部对应通知
    public List<Notification> getNotificationGradeId(String gradeId);
}
