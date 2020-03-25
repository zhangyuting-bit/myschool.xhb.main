package com.zb.mapper;

import com.zb.entity.Survey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SurveyMapper {
    //根据班级编号查询调查通知
    List<Survey>getSurveyByGradeId(@Param("gradeId") String gradeId);
}
