package com.zb.controller;

import com.zb.entity.Notification;
import com.zb.service.NotificationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class NotificationController {
    @Resource
    private NotificationService notificationService;

    //根据班级编号和通知类型编号获取全部对应通知
    @GetMapping("/getNotificationGradeId/{gradeId}")
    public List<Notification> getNotificationGradeId(@PathVariable("gradeId") String gradeId) {
        return notificationService.getNotificationGradeId(gradeId);
    }


}
