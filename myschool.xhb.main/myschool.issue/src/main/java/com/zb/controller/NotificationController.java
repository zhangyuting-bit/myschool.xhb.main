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
    @GetMapping("/getNotificationByUserId")
    public  Dto<List<Notification>> getNotificationByUserId(
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
    @GetMapping("/getNotStatus")
    public Dto<Integer> getStatus(String userId,String gradeId){
        return DtoUtil.returnSuccess("ok",notificationService.getStatus(userId,gradeId));
    }

    //根据用户编号和通知编号删除通知信息
    @GetMapping("/delNotOne")
    public Dto<Integer> delNotOneByNotIdAndUserId(String userId,String notificationId){
        return DtoUtil.returnSuccess("ok",notificationService.delNotOneByNotIdAndUserId(userId, notificationId));
    }

    //撤销调查信息
    @GetMapping("/returnNotification")
    public void returnSurvey(String notificationId,String gradeId){
        notificationService.returnNot(notificationId, gradeId);
    }

    //获取撤销信息
    @GetMapping("/getNotDelStatus/{gradeId}")
    public Dto<String> getNotDelStatus(@PathVariable("gradeId") String gradeId){
        return DtoUtil.returnSuccess("ok",notificationService.getNotDelStatus(gradeId));
    }

    //根据token获取用户编号
    @GetMapping("/getUserIdByToken/{token}")
    public String getUserIdByToken(@PathVariable("token") String token){
        return notificationService.getUserIdByToken(token);
    }
}
