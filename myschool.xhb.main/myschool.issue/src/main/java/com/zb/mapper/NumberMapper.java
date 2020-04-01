package com.zb.mapper;

import com.zb.entity.Number;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NumberMapper {
    //添加学生学号
    Integer addNumber(Number number);

    //根据班级编号查询学生学号
    List<Number> getNumberByGradeId(@Param("gradeId")String gradeId);

    //根据班级编号删除学生学号信息
    Integer updateNumber(@Param("numberId")String numberId);
}
