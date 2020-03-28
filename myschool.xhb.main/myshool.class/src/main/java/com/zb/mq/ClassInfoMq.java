package com.zb.mq;

import com.rabbitmq.client.Channel;
import com.zb.config.RabbitConfig;
import com.zb.pojo.Class_Job;
import com.zb.pojo.Class_add;
import com.zb.pojo.Class_info;
import com.zb.service.ClassInfoService;
import com.zb.service.ClassleaveService;
import com.zb.util.IdWorker;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ClassInfoMq {
    @Autowired
    private ClassInfoService classInfoService;
    @RabbitListener(queues = RabbitConfig.CLASSQUEUE)
    public void send(Class_add classAdd, Message message, org.springframework.messaging.Message messages,Channel channel){
        Long tag= (Long)messages.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        Class_info classInfo=new Class_info();
        classInfo.setId(IdWorker.getId());
        classInfo.setCall("孟老师"); //这个地方调用登录的feign来传用户的名称
        classInfo.setUser_id(classAdd.getTeacher_id());
        classInfo.setClass_number(classAdd.getClass_number());
        classInfo.setJurisdiction("最高管理员"); //一开始添加 -1表示在本班级的最高级别
        classInfo.setClass_subject(classAdd.getClass_subject());
        classInfo.setState(1);
        try {
            channel.basicAck(tag,false);
            classInfoService.addClassInfo(classInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
