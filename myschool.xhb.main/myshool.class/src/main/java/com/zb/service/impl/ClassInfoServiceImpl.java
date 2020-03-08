package com.zb.service.impl;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.mapper.ClassInfoMapper;
import com.zb.mapper.ClassMapper;
import com.zb.pojo.Class_add;
import com.zb.pojo.Class_info;
import com.zb.service.ClassInfoService;
import com.zb.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassInfoServiceImpl implements ClassInfoService {
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Autowired
    private ClassMapper classMapper;

    @Override
    public Dto addClassInfo(Class_info classInfo) {
        Class_info classInfoBy = classInfoMapper.getClassInfoBy(classInfo.getClass_number());
        if(classInfoBy==null){
            Class_info info=new Class_info();
            info.setId(IdWorker.getId());
            info.setCall("孟老师"); //这个地方调用登录的feign来传用户的名称
            info.setUser_id(classInfo.getUser_id());
            info.setClass_number(classInfo.getClass_number());
            info.setJurisdiction_id(1); //一开始添加 1表示在本班级的最高级别
            info.setClass_subject(classInfo.getClass_subject());
            classInfoMapper.addClassInfo(info);
        }else {
            classInfoBy.setCreatedTime(classInfo.getCreatedTime());
            classInfoBy.setUpdatedTime(classInfo.getUpdatedTime());
        }
        return DtoUtil.returnSuccess("ok",classInfoBy);
    }

    @Override
    public int addClassInfo(Integer class_number, Integer user_id) {
        Class_add classBy = classMapper.getClassBy(class_number);
            //根据你登录的身份来判断你是家人/学生 还是教师
            if(user_id==2){
                Class_info classInfo=new Class_info();
                classInfo.setId(IdWorker.getId());
                classInfo.setClass_number(classBy.getClass_number());
                classInfo.setUser_id(user_id);
                classInfo.setCall("");
                classInfoMapper.addClassInfo(classInfo);
            }
        return 0;
    }
}
