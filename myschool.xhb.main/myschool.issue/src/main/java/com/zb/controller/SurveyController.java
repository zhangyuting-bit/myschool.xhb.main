package com.zb.controller;

import com.zb.entity.Survey;
import com.zb.service.SurveyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
