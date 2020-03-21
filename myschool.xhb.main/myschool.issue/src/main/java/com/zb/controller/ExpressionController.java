package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.Expression;
import com.zb.service.impl.ExpressionServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ExpressionController {
    @Resource
    private ExpressionServiceImpl expressionService;

    //获取全部表情信息
    @GetMapping("/getExpressionAll")
    public Dto getExpressionAll(){
        return DtoUtil.returnSuccess("ok",expressionService.getExpressionAll());
    }

    //根据表情编号获取表情信息
    @GetMapping("/getExpressionById/{expressionId}")
    public Dto getExpressionById(@PathVariable("expressionId") Integer expressionId){
        return DtoUtil.returnSuccess("ok",expressionService.getExpressionById(expressionId));
    }

}
