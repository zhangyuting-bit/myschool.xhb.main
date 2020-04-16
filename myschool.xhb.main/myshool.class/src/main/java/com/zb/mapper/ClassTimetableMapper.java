package com.zb.mapper;

import com.zb.pojo.Class_Timetable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ClassTimetableMapper {
    int addtimetable(Class_Timetable timetable);
    //展示班级的课程信息
    Class_Timetable findgettimetableBy(@Param("class_number")Integer class_number);
    //重置课程表
    int updatatimetable(Class_Timetable timetable);
    //删除课程
    int deletetimetable(@Param("class_number")Integer class_number);
}
