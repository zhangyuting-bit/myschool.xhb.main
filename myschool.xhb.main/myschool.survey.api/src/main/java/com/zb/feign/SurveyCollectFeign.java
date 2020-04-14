package com.zb.feign;

import com.zb.entity.Survey;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "SURVEYSERVER")
public interface SurveyCollectFeign {
    //根据调查编号获取集合单例信息
    @GetMapping("/getBySurveyId/{surveyId}")
    public Survey getBySurveyId(@PathVariable("surveyId")String surveyId);
}
