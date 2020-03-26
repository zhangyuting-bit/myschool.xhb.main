package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.Survey;
import com.zb.service.SurveyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SurveyController {
    @Resource
    private SurveyService surveyService;

    //根据班级编号查询调查通知
    @GetMapping("/getSurveyByGradeId/{gradeId}")
    public List<Survey> getSurveyByGradeId(@PathVariable("gradeId") String gradeId) {
        return surveyService.getSurveyByGradeId(gradeId);
    }

    //根据调查编号查询调查信息
    @GetMapping("/getSurveyBySurveyId/{surveyId}")
    public Dto<Survey> getSurveyBySurveyId(@PathVariable("surveyId") String surveyId) {
        return DtoUtil.returnSuccess("ok",surveyService.getSurveyBySurveyId(surveyId));
    }

    //添加新调查
    @RequestMapping("/addSurvey")
    public Dto<Survey> addSurvey(Survey survey) {
        return DtoUtil.returnSuccess("ok",surveyService.addSurvey(survey));
    }

    //学生端实时显示信息
    @GetMapping("/getSurStu/{gradeId}")
    public Dto<Survey> getSurStu(@PathVariable("gradeId") String gradeId){
        return DtoUtil.returnSuccess("ok",surveyService.getSurStu(gradeId));
    }

    //修改结束时间
    @RequestMapping("/updateSurEndTimeOne")
    public Dto<Integer> updateSurEndTimeOne(String endTime, String surveyId) {
        return DtoUtil.returnSuccess("ok",surveyService.updateSurEndTimeOne(endTime, surveyId));
    }

    //把调查状态修改为已结束
    @RequestMapping("/updateSurEndTime/{surveyId}")
    public Dto<Integer> updateSurEndTime(@PathVariable("surveyId") String surveyId) {
        return DtoUtil.returnSuccess("ok",surveyService.updateSurEndTime(surveyId));
    }

}
