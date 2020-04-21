package com.zb.service.impl;

import com.rabbitmq.tools.json.JSONUtil;
import com.zb.config.RabbitConfig;
import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.feign.UserFeignClient;
import com.zb.mapper.ClassInfoMapper;
import com.zb.mapper.ClassMapper;
import com.zb.pojo.*;
import com.zb.service.ClassInfoService;
import com.zb.util.IdWorker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassInfoServiceImpl implements ClassInfoService {
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public Dto addClassInfo(Class_info classInfo) {
        return DtoUtil.returnSuccess("ok",classInfoMapper.addClassInfo(classInfo));
    }

    @Override
    public int addnumberClassInfo(Class_info classInfo,Integer class_number, String token) {
        //这里调用登录用户的feign的接口获取用户的id和名称
        UserInfo userInfoByToken = userFeignClient.getUserInfoByToken(token);
            if(userInfoByToken.getCodeType()==1){
                Class_Job job=new Class_Job();
                job.setId(IdWorker.getId());
                job.setClass_number(class_number);
                job.setCall("老师");
                job.setJurisdiction("任课老师");
                job.setRelationship("");
                job.setUser_id(userInfoByToken.getCodeType());
                job.setJurisdiction_id(1);
                job.setState(0);
               return classInfoMapper.addClassJob(job);
            }
            //根据你登录的身份来判断你是家人 还是教师
            if(userInfoByToken.getCodeType()==2){
                Class_Job jobs=new Class_Job();
                jobs.setId(IdWorker.getId());
                jobs.setClass_number(class_number);
                jobs.setUser_id(userInfoByToken.getCodeType());
                jobs.setCall(classInfo.getCall());
                jobs.setRelationship(classInfo.getRelationship());
                jobs.setJurisdiction_id(2);
                jobs.setJurisdiction("0");
                jobs.setState(0);
                System.out.println("班级内的名称："+classInfo.getCall());
                System.out.println("内部关系："+classInfo.getRelationship());
                int count = classInfoMapper.addClassJob(jobs);
                if(count>0){
                    Class_Jobinfo jobinfo=new Class_Jobinfo();
                    jobinfo.setNumber(jobs.getUser_id());
                    jobinfo.setCall_name(jobs.getCall());
                    jobinfo.setClass_number(jobs.getClass_number());
                    rabbitTemplate.convertAndSend(RabbitConfig.myexchange, "inform.classinfo", jobinfo);
                    return count;
                }
            }
        return 0;
    }

    @Override
    public List<Class_info> findClassinfoBy(Integer class_number) {

        return classInfoMapper.findClassBy(class_number);
    }

    @Override
    public int updateClassInfo(Class_info classInfo) {
        return classInfoMapper.updateClassInfo(classInfo);
    }

    @Override
    public Class_info getClassInfoBy(String id) {
        return classInfoMapper.getClassInfoBy(id);
    }

    @Override
    public Dto classinfojob(Integer class_number) {
        Class_info info=null;
        //根据班级号获取任务表中的要加入这个班级的信息
        List<Class_Job> classjob = classInfoMapper.findclassjob(class_number);
        if(classjob!=null) {
            String id=null;
            for (Class_Job job : classjob) {
                id=job.getId();
                info = new Class_info();
                info.setId(IdWorker.getId());
                info.setClass_number(job.getClass_number());
                info.setJurisdiction_id(job.getJurisdiction_id());
                info.setUser_id(job.getUser_id());
                info.setCall(job.getCall());
                info.setRelationship(job.getRelationship());
                info.setJurisdiction(job.getJurisdiction());
                info.setState(1);
            }
            int count = classInfoMapper.classdeletejob(id);
            System.out.println(count+"@@@@@@@@@@@@@@@@@");
            classInfoMapper.addClassInfo(info);
        }
        return DtoUtil.returnSuccess("ok") ;
    }

    @Override
    public int classdeletejobs(String id) {
        return classInfoMapper.classdeletejob(id);
    }

    @Override
    public Dto findclassjob(Integer class_number) {
        Class_Job job=null;
        List<Class_Job> findclassjob = classInfoMapper.findclassjob(class_number);
       if(findclassjob!=null){
           for (Class_Job classJob : findclassjob) {
               job=new Class_Job();
               job.setId(classJob.getId());
               job.setCall(classJob.getCall());
               job.setRelationship(classJob.getRelationship());
           }
       }
        return DtoUtil.returnSuccess("ok",job);
    }

    @Override
    public List<Class_Job> findClassjobBy(Integer class_number) {
        return classInfoMapper.findclassjob(class_number);
    }

    @Override
    public List<Class_info> findinfouserid(Integer user_id) {
        return classInfoMapper.getClassInfouserid(user_id);
    }

}
