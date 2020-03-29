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

    //根据用户编号查询调查通知
    @GetMapping("/getSurveyByUserId/{userId}")
    public Dto<List<Survey>> getSurveyByUserId(@PathVariable("userId") String userId) {
        return DtoUtil.returnSuccess("ok",surveyService.getSurveyByUserId(userId));
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
    @GetMapping("/getSurStu")
    public Dto<Survey> getSurStu(String userId,String gradeId){
        return DtoUtil.returnSuccess("ok",surveyService.getSurStu(userId,gradeId));
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

    //删除推送消息
    @GetMapping("/delStuSur")
    public void delStuSur(String userId, String surveyId, String gradeId){
        surveyService.delStuSur(userId, surveyId, gradeId);
    }

    //添加推送状态
    @GetMapping("/addOkSur")
    public void addStatus(String gradeId) {
        surveyService.addStatus(gradeId);
    }

    //获取推送状态
    @GetMapping("/getSurStatus")
    public Dto<Integer> getStatus(String userId,String gradeId){
        return DtoUtil.returnSuccess("ok",surveyService.getStatus(userId,gradeId));
    }

}
