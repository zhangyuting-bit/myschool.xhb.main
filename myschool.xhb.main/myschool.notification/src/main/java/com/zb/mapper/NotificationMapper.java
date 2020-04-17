package com.zb.mapper;

import com.zb.entity.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationMapper {
    //根据班级编号获取全部通知信息
    List<Notification>getNotificationByGrade(@Param("gradeId")String gradeId);

    //根据通知编号获取通知信息
    Notification getNotificationById(@Param("notificationId")String notificationId);

    //添加新的通知信息
    Integer addNotification(Notification notification);

    //修改通知的语音路径或视频路径
    Integer updateVdoAndAudio(@Param("audioSrc")String audioSrc,@Param("videoSrc")String videoSrc,@Param("notificationId")String notificationId);

    //修改结束时间
    Integer updateEndTimeOne(@Param("endTime")String endTime,@Param("notificationId")String notificationId);

    //根据通知编号撤销通知
    Integer delNot(@Param("notificationId")String notificationId);
}
