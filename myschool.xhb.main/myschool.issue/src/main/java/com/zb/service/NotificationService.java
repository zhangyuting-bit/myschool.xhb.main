package com.zb.service;

import com.zb.entity.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationService {
    //根据班级编号和通知类型编号获取全部对应通知
    public List<Notification> getNotificationGradeId(Integer typeId,String gradeId);

    //根据通知编号获取通知信息
    public Notification getNotificationById(String notificationId);

    //添加新的通知信息
    public Notification addNotification(Notification notification);

    //学生端实时显示信息
    public Notification getNocStu(Integer typeId,String gradeId);

    //修改结束时间
    public Integer updateEndTimeOne(String endTime,String notificationId);

    //把通知状态修改为已结束
    public Integer updateEndTime(String notificationId);

}
