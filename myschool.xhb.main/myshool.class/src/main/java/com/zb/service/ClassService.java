package com.zb.service;

import com.qiniu.http.Response;
import com.zb.pojo.Class_Subject;
import com.zb.pojo.Class_add;
import com.zb.pojo.Class_age_real;
import com.zb.pojo.Class_real;

import java.io.File;
import java.util.List;

public interface ClassService {
    //创建班级
    int addClass(Class_add classAdd);
    //上传到七牛云
    Response uploadFile(File file) throws Exception;
    //查询学历
    List<Class_real> getReal();
    //查询学科
    List<Class_Subject> getSubject();
    List<Class_age_real> getAgeReal(Integer real_id);
    //根据班级号查询班级
    Class_add findClassBy(Integer class_number);
}
