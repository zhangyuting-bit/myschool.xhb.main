package com.zb.service;

import com.zb.dto.Dto;
import com.zb.pojo.Class_info;

public interface ClassInfoService {
    Dto addClassInfo(Class_info classInfo);
    //根据搜索的班级号来加入班级
    int addClassInfo(Integer class_number,Integer user_id);
}
