package com.zb.controller;

import com.alibaba.fastjson.JSON;
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
            String userId) {
        return DtoUtil.returnSuccess("ok",notificationService.getNotificationGradeId(typeId,userId));
    }

    //添加新的通知信息
    @RequestMapping("/addNotification")
    public Dto<Notification> addNotification(Notification notification) {
        return DtoUtil.returnSuccess("ok",notificationService.addNotification(notification));
    }

    //根据通知编号获取通知信息
    @GetMapping("/getNotificationById/{notificationId}")
    public Dto<Notification> getNotification(@PathVariable("notificationId") String notificationId) {
        return DtoUtil.returnSuccess("ok",notificationService.getNotification(notificationId));
    }

    //学生端实时显示信息
    @GetMapping("/getNocStu")
    public Dto<Notification> getNocStu(@RequestParam(value = "typeId",required = false,defaultValue = "0") Integer typeId,
                         String userId,String gradeId){
        return DtoUtil.returnSuccess("ok",notificationService.getNocStu(typeId, userId,gradeId));
    }

    //修改结束时间
    @RequestMapping("/updateEndTimeOne")
    public Dto<Integer> updateEndTimeOne(String endTime, String notificationId) {
        return DtoUtil.returnSuccess("ok",notificationService.updateEndTimeOne(endTime, notificationId));
    }

    //把通知状态修改为已结束
    @RequestMapping("/updateEndTime/{notificationId}")
    public Dto<Integer> updateEndTime(@PathVariable("notificationId") String notificationId) {
        return DtoUtil.returnSuccess("ok",notificationService.updateEndTime(notificationId));
    }

    //删除推送消息
    @GetMapping("/delStuNoc")
    public void delStuNoc(String userId,String notificationId,String gradeId) {
        notificationService.delStuNoc(userId,notificationId,gradeId);
    }

    //添加推送状态
    @GetMapping("/addOkNoc")
    public void addStatus(String gradeId,String teacherId) {
        notificationService.addStatus(gradeId,teacherId);
    }

    //获取推送状态
    @GetMapping("/getStatus")
    public Dto<Integer> getStatus(String userId,String gradeId){
        return DtoUtil.returnSuccess("ok",notificationService.getStatus(userId,gradeId));
    }
}
