package com.zb.Job;

import com.zb.config.RabbitConfig;
import com.zb.entity.JobTask;
import com.zb.feign.ClassMassagesFeign;
import com.zb.mapper.JobTaskMapper;
import com.zb.pojo.Class_add;
import com.zb.pojo.Class_info;
import com.zb.util.RedisUtil;
import com.zb.vo.SendVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Component
public class JobTaskService {
    @Resource
    private JobTaskMapper jobTaskMapper;

    @Resource
    private ClassMassagesFeign classMassagesFeign;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 60000)
    public void send(){
        List<JobTask>jobTasks=jobTaskMapper.getJobTaskAll();;
        for (JobTask jobTask:jobTasks) {
            Date d=new Date();
            int h=d.getHours();
            int m=d.getMinutes();
            String ok=h+""+m;
            String[]msg=jobTask.getTaskTime().split(":");
            String jh="";
            for (int i = 0; i <msg.length ; i++) {
                jh+=msg[i];
            }
            if (Integer.parseInt(jh)==Integer.parseInt(ok)){
               List<Class_info>infos=classMassagesFeign.classinfo(Integer.parseInt(jobTask.getGradeId()));
               Class_add class_add=classMassagesFeign.showclass(jobTask.getGradeId());
                for (Class_info class_info:infos) {
                    SendVo sendVo=new SendVo();
                    sendVo.setTeacherName(class_add.getTeacherName());
                    sendVo.setClassName(class_add.getClass_Name());
                    sendVo.setUserId(class_info.getUser_id().toString());
                    rabbitTemplate.convertAndSend(RabbitConfig.myexchange, RabbitConfig.sendKey, sendVo);
                }
            }
        }
    }
}
