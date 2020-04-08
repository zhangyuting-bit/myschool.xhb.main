package com.zb.service;

import com.zb.entity.Notification;
import com.zb.entity.Survey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SurveyService {
     //根据用户编号查询调查通知
     public List<Survey> getSurveyByUserId(String userId);

     //根据调查编号查询调查信息
     public Survey getSurveyBySurveyId(String surveyId);

     //添加新调查
     public Survey addSurvey(Survey survey);

     //学生端实时显示信息
     public Survey getSurStu( String userId,String gradeId);

     //修改结束时间
     public Integer updateSurEndTimeOne(String endTime,String surveyId);

     //删除推送消息
     public void delStuSur(String userId, String surveyId, String gradeId);

     //获取推送状态
     public Integer getStatus(String userId, String gradeId);

     //根据用户编号和调查编号删除成绩信息
     public Integer delSurvey(String userId,String surveyId);

     //撤销调查信息
     public void returnSurvey(String surveyId,String gradeId);

     //获取撤销信息
     public String getSurDelStatus(String gradeId);

}
