package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.SubjectAdd;
import com.zb.service.SubjectAddService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SubjectAddController {
    @Resource
    private SubjectAddService subjectAddService;

    ///新增科目信息
    @RequestMapping("/addSubjectAdd")
    public Dto<Integer> addSubjectAdd(SubjectAdd subjectAdd) {
        return DtoUtil.returnSuccess("ok",subjectAddService.addSubjectAdd(subjectAdd));
    }

    //根据班级编号查询新增科目
    @GetMapping("/getByGradeId/{gradeId}")
    public Dto<List<SubjectAdd>> getByGradeId(@PathVariable("gradeId") String gradeId) {
        return DtoUtil.returnSuccess("ok",subjectAddService.getByGradeId(gradeId));
    }

    //删除新增科目
    @GetMapping("/delSubjectAdd/{id}")
    public Dto<Integer> delSubjectAdd(@PathVariable("id") String id) {
        return DtoUtil.returnSuccess("ok",subjectAddService.delSubjectAdd(id));
    }
}
