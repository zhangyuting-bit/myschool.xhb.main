package com.zb.mapper;

import com.zb.entity.Subject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubjectMapper {
    //添加考试科目
    Integer addSubject(Subject subject);

    //根据编号
    List<Subject>getSubjectByScoreId(@Param("scoreId")String scoreId);
}
