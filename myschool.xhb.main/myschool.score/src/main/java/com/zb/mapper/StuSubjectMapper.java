package com.zb.mapper;

import com.zb.entity.StuSubject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StuSubjectMapper {
    ///添加科目成绩
    Integer addStuSubject(StuSubject stuSubject);

    //根据学号编号获取科目成绩
    StuSubject getStuSubjectByNumberId(@Param("numberId")String numberId,@Param("subjectId")String subjectId);

    //根据科目成绩编号获取最高分
    StuSubject getStuSubjectHigh(@Param("subjectId")String subjectId);

    //根据科目成绩编号获取最低分
    StuSubject getStuSubjectShort(@Param("subjectId")String subjectId);

    //根据科目编号获取科目成绩
    List<StuSubject>getStuSubjectBySubjectId(@Param("subjectId")String subjectId);

    //根据科目编号和学号编号获取学生成绩
    StuSubject getStuSubjectBySubjectAndNumberId(@Param("subjectId")String subjectId,@Param("numberId")String numberId);

    //根据科目编号删除科目成绩
    Integer delStuSubject(@Param("subjectId")String subjectId);
}
