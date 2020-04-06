package com.zb.service;

import com.zb.entity.Notification;
import com.zb.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationService {
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
    public void addStatus(String gradeId,String teacherId);

    //获取推送状态
    public Integer getStatus(String userId,String gradeId);

    //根据通知编号获取通知信息
    public Notification getNotification(String notificationId);

    //根据用户编号和通知编号删除通知信息
    public Integer delNotOneByNotIdAndUserId(String userId,String notificationId);

    //撤销通知信息
    public void returnNot(String notificationId,String gradeId);

    //获取撤销信息
    public String getNotDelStatus(String userId,String gradeId);

    //删除撤销信息
    public void delStatus(String userId,String gradeId);
}
