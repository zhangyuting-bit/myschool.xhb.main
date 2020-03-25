package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.Notification;
import com.zb.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class NotificationController {
    
    @Resource
    private NotificationService notificationService;

    ////根据班级编号和通知类型编号获取全部对应通知
    @GetMapping("/getNotificationGradeId")
    public Dto<List<Notification>> getNotificationGradeId(
            @RequestParam(value = "typeId",required = false,defaultValue = "0") Integer typeId,
            String gradeId) {
        return DtoUtil.returnSuccess("ok",notificationService.getNotificationGradeId(typeId,gradeId));
    }

    //添加新的通知信息
    @RequestMapping("/addNotification")
    public Dto<Notification> addNotification(Notification notification) {
        return DtoUtil.returnSuccess("ok",notificationService.addNotification(notification));
    }

    //根据通知编号获取通知信息
    @GetMapping("/getNotificationById/{notificationId}")
    public Dto<Notification> getNotificationById(@PathVariable("notificationId") String notificationId) {
        return DtoUtil.returnSuccess("ok",notificationService.getNotificationById(notificationId));
    }

    //学生端实时显示信息
    @GetMapping("/getNocStu")
    public Dto<Notification> getNocStu(@RequestParam(value = "typeId",required = false,defaultValue = "0") Integer typeId,
                         String gradeId){
        return DtoUtil.returnSuccess("ok",notificationService.getNocStu(typeId, gradeId));
    }

    //修改结束时间
    @RequestMapping("updateEndTime")
    public Dto<Integer> updateEndTime(String endTime, String notificationId) {
        return DtoUtil.returnSuccess("ok",notificationService.updateEndTime(endTime, notificationId));
    }
}
