package com.zb.service;

import com.qiniu.http.Response;
import com.zb.pojo.Class_Timetable;

import java.io.File;

public interface ClassTimetableService {
    //上传到七牛云
    Response uploadFile(File file) throws Exception;
    int addtimetable(Class_Timetable timetable);
    Class_Timetable findgettimetableBy(Integer class_number);
    //重置课程表
    int updatatimetable(Class_Timetable timetable);
    //删除课程
    int deletetimetable(Integer class_number);
}
