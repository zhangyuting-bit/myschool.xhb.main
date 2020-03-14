package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.AddExpression;
import com.zb.service.AddExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddExpressionController {
    @Autowired
    private AddExpressionService addExpressionService;

    @RequestMapping("/addExpression")
    public Dto addExpression(AddExpression addExpression){
        return DtoUtil.returnSuccess("ok",addExpressionService.addAddExpression(addExpression));
    }
}
