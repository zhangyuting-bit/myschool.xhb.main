package com.zb.controller;

import com.zb.entity.Mudel;
import com.zb.service.MudelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/issue/")
public class MudelController {
    @Autowired
    private MudelService mudelService;

    //根据功能类型编号获取模板
    @GetMapping("getMudelByTypeId/{typeId}")
    public List<Mudel> getMudelByTypeId(@PathVariable("typeId") Integer typeId) {
        return mudelService.getMudelByTypeId(typeId);
    }

    // 根据模板编号获取模板信息
    @GetMapping("getMudelById/{mudelId}")
    public Mudel getMudelById(@PathVariable("mudelId")Integer mudelId) {
        return mudelService.getMudelById(mudelId);
    }

}
