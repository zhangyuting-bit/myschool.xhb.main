package com.zb.controller;

import com.zb.service.ClassTaskService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ClassTaskController {
    @Resource
    private ClassTaskService classTaskService;

    @RequestMapping("/updateTaskCount/{id}")
    public Integer updateTaskCount(@PathVariable("id") String id){
        return classTaskService.updateTaskCount(id);
    }
}
