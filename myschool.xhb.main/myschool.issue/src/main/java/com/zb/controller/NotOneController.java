package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.NotOne;
import com.zb.entity.Notification;
import com.zb.service.NotOneService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class NotOneController {
    @Resource
    private NotOneService service;

    //根据班级编号和通知类型编号获取全部对应通知
    @GetMapping("/getOneByUserIdAll/{userId}")
    public  Dto<List<NotOne>> getOneAll(@PathVariable("userId") String userId) {
        return DtoUtil.returnSuccess("ok",service.getOneAll(userId));
    }

    //根据班级编号和通知类型编号获取全部对应通知
    @GetMapping("/getNotOneByUserId")
    public  List<NotOne> getOneByUserId(@RequestParam(value = "typeId",required = false) Integer typeId
            ,@RequestParam(value = "userId",required = false)String userId) {
        return service.getOneByUserId(typeId,userId);
    }

    //根据用户编号和通知编号删除通知信息
    @GetMapping("/delNotOne")
    public Integer delNotOneByNotIdAndUserId(@RequestParam(value = "functionId",required = false)String functionId
            ,@RequestParam(value = "typeId",required = false) Integer typeId){
        return service.delNotOneByNotIdAndUserId(functionId,typeId);
    }

    //根据消息编号和用户编号和类型编号删除信息
    @GetMapping("/delNotOneByNotId")
    public Dto<Integer> delNotOneByNotId(String functionId,String userId,Integer typeId){
        return DtoUtil.returnSuccess("ok",service.delNotOneByNotId(functionId, userId, typeId));
    }

}
