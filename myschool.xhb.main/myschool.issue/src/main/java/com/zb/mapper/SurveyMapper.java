package com.zb.mapper;

import com.zb.entity.Survey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SurveyMapper {
    //根据班级编号查询调查通知
    List<Survey>getSurveyByGradeId(@Param("gradeId") String gradeId);

    //根据调查编号查询调查信息
    Survey getSurveyBySurveyId(@Param("surveyId")String surveyId);

    //添加新调查
    Integer addSurvey(Survey survey);

    //更改音频路径
    Integer updateAudio(Survey survey);

    //修改结束时间
    Integer updateSurEndTimeOne(@Param("endTime")String endTime,@Param("surveyId")String surveyId);

    //把调查状态修改为已结束
    Integer updateSurEndTime(@Param("surveyId")String surveyId);
}
