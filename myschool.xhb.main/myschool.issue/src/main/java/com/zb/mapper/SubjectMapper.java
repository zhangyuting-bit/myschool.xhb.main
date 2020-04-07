package com.zb.mapper;

import com.zb.entity.Subject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubjectMapper {
    ///添加考试科目
    Integer addSubject(Subject subject);

    //根据编号
    List<Subject>getSubjectByScoreId(@Param("scoreId")String scoreId);

    //修改科目信息
    Integer updateSubject(Subject subject);

    //根据成绩编号删除科目信息
    Integer delSubject(@Param("scoreId")String scoreId);
}
