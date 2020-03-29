package com.zb.mapper;

import com.zb.entity.Survey;
import com.zb.entity.SurveyOne;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SurveyOneMapper {
    //添加调查信息
    Integer addSurveyOne(SurveyOne surveyOne);

    //根据用户编号查询调查信息
    List<SurveyOne>getSurveyOneByUserId(@Param("userId") String userId);

}
