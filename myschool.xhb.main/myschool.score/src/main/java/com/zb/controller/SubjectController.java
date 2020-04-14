package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.Subject;
import com.zb.service.SubjectService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SubjectController {
    @Resource
    private SubjectService subjectService;

    ///添加考试科目
    @RequestMapping("/addSubject")
    public Dto<Integer> addSubject(Subject subject) {
        return DtoUtil.returnSuccess("ok",subjectService.addSubject(subject));
    }
}
