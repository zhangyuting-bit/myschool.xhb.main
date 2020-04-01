package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.Number;
import com.zb.service.NumberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class NumberController {
    @Resource
    private NumberService numberService;

    //添加学生学号
    @RequestMapping("/addNumber")
    public Dto<Integer> addNumber(Number number) {
        return DtoUtil.returnSuccess("ok",numberService.addNumber(number));
    }

    //根据班级编号查询学生学号
    @GetMapping("/getNumberByGradeId/{gradeId}")
    public Dto<Number> getNumberByGradeId(@PathVariable("gradeId") String gradeId) {
        return  DtoUtil.returnSuccess("ok",numberService.getNumberByGradeId(gradeId));
    }

    //根据班级编号删除学生学号信息
    @GetMapping("/delNumberByGradeId/{numberId}")
    public Dto<Integer> updateNumber(@PathVariable("numberId") String numberId) {
        return DtoUtil.returnSuccess("ok",numberService.updateNumber(numberId));
    }
}
