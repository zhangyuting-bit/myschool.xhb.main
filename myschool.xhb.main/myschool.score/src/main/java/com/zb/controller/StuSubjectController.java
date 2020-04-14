package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.StuSubject;
import com.zb.service.StuSubjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StuSubjectController {
    @Resource
    private StuSubjectService stuSubjectService;

    //添加科目成绩
    @RequestMapping("/addStuSubject")
    public Dto<Integer> addStuSubject(StuSubject stuSubject) {
        return DtoUtil.returnSuccess("ok",stuSubjectService.addStuSubject(stuSubject));
    }
}
