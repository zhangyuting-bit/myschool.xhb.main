package com.zb.controller;

import com.zb.util.ExportUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ExportController {
    @Resource
    private ExportUtil exportUtil;

    //根据分数编号获取考试信息
    @GetMapping("/ExportExcel/{scoreId}")
    public String ExportExcel(@PathVariable("scoreId") String scoreId){
        return exportUtil.ExportExcel(scoreId);
    }
}
