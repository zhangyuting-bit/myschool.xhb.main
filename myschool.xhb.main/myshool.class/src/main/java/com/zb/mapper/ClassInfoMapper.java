package com.zb.mapper;

import com.zb.pojo.Class_Job;
import com.zb.pojo.Class_info;
import com.zb.pojo.Jurisdiction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassInfoMapper {
    int addClassInfo(Class_info classInfo);
    Class_info getClassInfoBy(@Param("id")String id);
    Class_info getClassInfouserid(@Param("user_id")Integer user_id);
    int updateClassInfo(Class_info classInfo);
    //根据班级号查询班级的总人数
    int classcount(@Param("class_number")Integer class_number);
    //获取这个班级内部成员信息
    List<Class_info> findClassBy(@Param("class_number")Integer class_number);
    //根据添加的状态来通知本班级的老师审核
    int classinfostate();
    //添加一条任务表信息
    int addClassJob(Class_Job classJob);
    //添加一条任务后并修改时间
    int updateClassJobTime(@Param("id")String id);
    //查询任务表中的全部信息
    List<Class_Job> findclassjob(@Param("class_number")Integer class_number);
    //根据id查询任务表中的信息
    Class_Job findClassjobBy(@Param("id")String id);
    //删除申请表中的信息
    int classdeletejob(@Param("id")String id);
}
