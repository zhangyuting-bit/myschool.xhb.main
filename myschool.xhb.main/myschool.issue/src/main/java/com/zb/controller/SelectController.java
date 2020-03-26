package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.Select;
import com.zb.service.SelectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class SelectController {
    @Resource
    private SelectService selectService;

    //添加新题目
    @RequestMapping("/addSelect")
    public Dto<Select> addSelect(Select select) {
        return DtoUtil.returnSuccess("ok",selectService.addSelect(select));
    }
}
