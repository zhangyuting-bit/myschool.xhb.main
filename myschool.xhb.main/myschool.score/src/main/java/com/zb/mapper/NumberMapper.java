package com.zb.mapper;

import com.zb.entity.StuNumber;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NumberMapper {
    ///添加学生学号
    Integer addNumber(StuNumber number);

    //根据班级编号查询学生学号
    List<StuNumber> getNumberByGradeId(@Param("gradeId")String gradeId);

    //根据班级编号删除学生学号信息
    Integer updateNumber(@Param("numberId")String numberId);

    //根据学号编号获取学号信息
    StuNumber getNumberByNumberId(@Param("numberId")String numberId);

    //根据班级编号和班级名称获取学号信息
    StuNumber getNumberByName(@Param("stuName")String stuName, @Param("gradeId")String gradeId);
}
