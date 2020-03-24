package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.NotPic;
import com.zb.service.NotPicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class NotPicController {
    @Resource
    private NotPicService notPicService;

    //根据通知编号查询图片
    @GetMapping("/getPicByFId/{functionId}")
    public Dto<List<NotPic>> getPicByFId(@PathVariable("functionId") String functionId) {
        return DtoUtil.returnSuccess("ok",notPicService.getPicByFId(functionId));
    }

    //根据通知编号查询状态为0的图片
    @GetMapping("/getPicByStatu/{functionId}")
    public Dto<NotPic> getPicByStatu(@PathVariable("functionId") String functionId) {
        return DtoUtil.returnSuccess("ok",notPicService.getPicByStatu(functionId));
    }
}
