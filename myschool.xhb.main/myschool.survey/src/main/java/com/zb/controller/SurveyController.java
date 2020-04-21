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
    @GetMapping("/getSurveyByUserId")
    public Dto<List<Survey>> getSurveyByUserId(Integer typeId, String userId) {
        return DtoUtil.returnSuccess("ok",surveyService.getSurveyByUserId(typeId,userId));
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

    //推送调查消息
    @GetMapping("/sendSurvey/{surveyId}")
    public void sendSurvey(@PathVariable("surveyId") String surveyId){
        surveyService.sendSurvey(surveyId);
    }

    //删除推送消息
    @GetMapping("/delStuSur")
    public void delStuSur(String userId, String surveyId, String gradeId){
        surveyService.delStuSur(userId, surveyId, gradeId);
    }

    //撤销调查信息
    @GetMapping("/returnSurvey/{surveyId}")
    public void returnSurvey(@PathVariable("surveyId") String surveyId){
        surveyService.returnSurvey(surveyId);
    }

    //获取撤销信息
    @GetMapping("/getSurDelStatus/{gradeId}")
    public Dto<String> getSurDelStatus(@PathVariable("gradeId")String gradeId){
        return DtoUtil.returnSuccess("ok",surveyService.getSurDelStatus(gradeId));
    }

    //根据调查编号获取集合单例信息
    @GetMapping("/getBySurveyId/{surveyId}")
    public Survey getBySurveyId(@PathVariable("surveyId")String surveyId){
        return surveyService.getBySurveyId(surveyId);
    }

    //根据班级编号获取调查信息
    @GetMapping("/getSurveyByGrade/{gradeId}")
    public List<Survey> getSurveyByGrade(@PathVariable("gradeId") String gradeId){
        return surveyService.getSurveyByGrade(gradeId);
    }
}
