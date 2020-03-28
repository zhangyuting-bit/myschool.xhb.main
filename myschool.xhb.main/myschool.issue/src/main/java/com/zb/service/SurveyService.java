package com.zb.service;

import com.zb.entity.Notification;
import com.zb.entity.Survey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SurveyService {
     //根据班级编号查询调查通知
     public List<Survey> getSurveyByGradeId(String gradeId);

     //根据调查编号查询调查信息
     public Survey getSurveyBySurveyId(String surveyId);

     //添加新调查
     public Survey addSurvey(Survey survey);

     //学生端实时显示信息
     public Survey getSurStu(String gradeId);

     //修改结束时间
     public Integer updateSurEndTimeOne(String endTime,String surveyId);

     //把调查状态修改为已结束
     public Integer updateSurEndTime(String surveyId);
}
