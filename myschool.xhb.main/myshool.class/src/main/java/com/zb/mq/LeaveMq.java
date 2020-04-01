package com.zb.mq;

import com.rabbitmq.client.Channel;
import com.zb.config.RabbitConfig;
import com.zb.pojo.Class_leave;
import com.zb.pojo.Leave_job;
import com.zb.service.ClassleaveService;
import com.zb.util.IdWorker;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LeaveMq {
    @Autowired
    private ClassleaveService classleaveService;
    @RabbitListener(queues = RabbitConfig.jobQueue)
    public void send(Leave_job leaveJob, Message message, Channel channel){
        Long tag= (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
            Class_leave classLeave=new Class_leave();
        System.out.println(leaveJob.getState()+"状态码~~~~~~~~~~~~~~~");
            classLeave.setId(IdWorker.getId());
            classLeave.setClass_number(leaveJob.getClass_number());
            classLeave.setStudent_id(leaveJob.getStudent_id());
            classLeave.setLeave_type(leaveJob.getLeave_type());
            classLeave.setLeave_time(leaveJob.getLeave_time());
            classLeave.setEnd_time(leaveJob.getEnd_time());
            classLeave.setState(leaveJob.getState());
            classLeave.setReason(leaveJob.getReason());
            classLeave.setLeaveimg(leaveJob.getLeaveimg());
        try {
            channel.basicAck(tag,false);
            classleaveService.addclassleave(classLeave);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
