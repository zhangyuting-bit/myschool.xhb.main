package com.zb.controller;

import com.zb.entity.Notification;
import com.zb.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class NotificationController {
    @Resource
    private NotificationService notificationService;

    //根据班级编号和通知类型编号获取全部对应通知
    @GetMapping("/getNotificationGradeId")
    public List<Notification> getNotificationGradeId(
            @RequestParam(value = "typeId",required = false,defaultValue = "0") Integer typeId,
            String gradeId) {
        return notificationService.getNotificationGradeId(typeId,gradeId);
    }

    //添加新的通知信息
    @RequestMapping("/addNotification")
    public Notification addNotification(Notification notification) {
        return notificationService.addNotification(notification);
    }


}
