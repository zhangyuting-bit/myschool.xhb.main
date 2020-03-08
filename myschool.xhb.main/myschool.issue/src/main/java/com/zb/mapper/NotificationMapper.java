package com.zb.mapper;

import com.zb.entity.Notification;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotificationMapper {
    //根据班级编号和通知类型编号获取全部对应通知
    List<Notification>getNotificationGradeId(@Param("gradeId")String gradeId);
}
