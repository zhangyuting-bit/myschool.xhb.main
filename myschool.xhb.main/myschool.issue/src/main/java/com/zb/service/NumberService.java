package com.zb.service;

import com.zb.entity.Number;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NumberService {
    //添加学生学号
    Integer addNumber(Number number);

    //根据班级编号查询学生学号
    List<Number> getNumberByGradeId(String gradeId);

    //根据班级编号删除学生学号信息
    Integer updateNumber(String numberId);
}
