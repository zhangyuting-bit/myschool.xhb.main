package com.zb.service;

import com.zb.entity.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationService {
    //根据班级编号获取全部通知信息
    List<Notification>getNotificationByGrade(String gradeId);

    //根据分数编号获取集合成绩单例
    public Notification getNotificationByNotId(String notificationId);

    //根据班级编号和通知类型编号获取全部对应通知
    public List<Notification> getNotificationGradeId(Integer typeId,String gradeId);

    //添加新的通知信息
    public Notification addNotification(Notification notification);

    //学生端实时显示信息
    public Notification getNocStu(Integer typeId,String userId,String gradeId);

    //修改结束时间
    public Integer updateEndTimeOne(String endTime,String notificationId);

    //删除推送消息
    public void delStuNoc(String userId,String notificationId,String gradeId);

    //添加推送状态
    public void addStatus(String gradeId,String notificationId);

    //根据通知编号获取通知信息
    public Notification getNotification(String notificationId);

    //撤销通知信息
    public void returnNot(String notificationId);

    //获取撤销信息
    public String getNotDelStatus(String gradeId);

    //根据token获取用户编号
    public String getUserIdByToken(String token);
}
