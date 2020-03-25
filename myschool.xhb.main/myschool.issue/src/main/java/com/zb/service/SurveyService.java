package com.zb.service;

import com.zb.entity.Survey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SurveyService {
     //根据班级编号查询调查通知
     public List<Survey> getSurveyByGradeId(String gradeId);
}
