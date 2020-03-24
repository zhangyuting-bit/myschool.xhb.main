package com.zb.service;

import com.zb.dto.Dto;
import com.zb.pojo.Class_Job;
import com.zb.pojo.Class_info;
import com.zb.pojo.Jurisdiction;

import java.util.List;

public interface ClassInfoService {
    Dto addClassInfo(Class_info classInfo);
    //根据搜索的班级号来加入班级
    int addClassInfo(Class_info info,Integer class_number,Integer user_id);
    //根据班号获取班级人员的信息
    List<Class_info> findClassinfoBy(Integer class_number);
    //修改在本班级内的学生信息
    int updateClassInfo(Class_info classInfo);
    //根据用户的id查询班级的信息进行修改
    Class_info getClassInfoBy(String id);
    //通过任务添加班级的信息
    Dto classinfojob(Integer class_number);
    //删除申请表中的信息
    int classdeletejobs(String id);
    //做监控查询是不是有要申请加入班级的信息
    Dto findclassjob(Integer class_number);
    //根据id查询申请人的信息
     List<Class_Job> findClassjobBy(Integer class_number);

}
