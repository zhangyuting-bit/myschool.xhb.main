package com.zb.service;

import com.qiniu.http.Response;
import com.zb.dto.Dto;
import com.zb.pojo.Class_info;
import com.zb.pojo.Class_leave;
import com.zb.pojo.Leave_job;

import java.io.File;
import java.util.List;

public interface ClassleaveService {
    int addleave(Leave_job leaveJob);
    //上传到七牛云
    Response uploadFile(File file) throws Exception;
    //同意请假
    Dto updateleaveAgree(String id);
    //拒绝请假
    Dto updateleavefuse(String id);
    //根据班级号获取请假的信息
    List<Leave_job> findleavenumber(Integer class_number);
    List<Leave_job> findleaveBy();
    //添加请假的信息
    int addclassleave(Class_leave classLeave);
    //历史记录请假的详情
    List<Class_leave> fingleaveList();
}
