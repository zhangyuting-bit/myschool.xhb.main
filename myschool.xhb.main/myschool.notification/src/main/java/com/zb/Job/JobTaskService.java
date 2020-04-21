package com.zb.Job;

import com.alibaba.fastjson.JSON;
import com.zb.config.RabbitConfig;
import com.zb.entity.JobTask;
import com.zb.feign.ClassMassagesFeign;
import com.zb.feign.UserFeignClient;
import com.zb.mapper.JobTaskMapper;
import com.zb.pojo.Class_add;
import com.zb.pojo.Class_info;
import com.zb.pojo.UserInfo;
import com.zb.util.RedisUtil;
import com.zb.vo.JobVo;
import com.zb.vo.SendVo;
import com.zb.vo.UserVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
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
        List<JobTask>jobTasks=addRedis();
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

    //获取定时任务集合
    public List<JobTask> addRedis(){
        List<JobTask>jobTasks=new ArrayList<>();
        String key="jobTask";
        if (redisUtil.hasKey(key)){
            Object o=redisUtil.get(key);
            JobVo jobVo= JSON.parseObject(o.toString(),JobVo.class);
            jobTasks=jobVo.getJobTasks();
        }else {
            JobVo jobVo=new JobVo();
            jobVo.setId(1);
            jobTasks=jobTaskMapper.getJobTaskAll();
            jobVo.setJobTasks(jobTasks);
            redisUtil.set(key,JSON.toJSONString(jobVo),240);
        }
        return jobTasks;
    }

}
