package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.service.impl.ExpressionServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ExpressionController {
    @Resource
    private ExpressionServiceImpl expressionService;

    @GetMapping("getExpressionAll")
    public Dto getExpressionAll(){
        return DtoUtil.returnSuccess("ok",expressionService.getExpressionAll());
    }
}
