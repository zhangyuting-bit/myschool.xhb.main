package com.zb.mapper;

import com.zb.pojo.Class_info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ClassInfoMapper {
    int addClassInfo(Class_info classInfo);
    Class_info getClassInfoBy(@Param("class_number")Integer class_number);
    int updateClassInfoTime(Class_info classInfo);
}
