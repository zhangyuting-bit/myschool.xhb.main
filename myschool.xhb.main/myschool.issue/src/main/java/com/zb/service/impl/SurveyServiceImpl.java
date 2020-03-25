package com.zb.service.impl;

import com.zb.entity.Survey;
import com.zb.mapper.SurveyMapper;
import com.zb.service.SurveyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SurveyServiceImpl implements SurveyService {
    @Resource
    private SurveyMapper surveyMapper;

    //根据班级编号查询调查通知
    @Override
    public List<Survey> getSurveyByGradeId(String gradeId) {
        return surveyMapper.getSurveyByGradeId(gradeId);
    }
}
