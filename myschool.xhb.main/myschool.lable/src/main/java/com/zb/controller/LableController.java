package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.pojo.Lable;
import com.zb.service.LableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LableController {

    @Autowired(required = false)
    LableService lableService;

    @RequestMapping(value = "/publiclables")
    public List<Lable> publiclables() {
        return lableService.listAllPublicLables();
    }

    @RequestMapping(value = "/personallables")
    public List<Lable> personallables(@RequestParam("userId") String userId) {
        return lableService.listPersonalLables(userId);
    }

    @RequestMapping(value = "/addlable")
    public Dto addlable(@RequestParam("lableContent") String lableContent,
                        @RequestParam("userId") String userId){
        Lable lable = new Lable();
        lable.setUserId(userId);
        lable.setLableContent(lableContent);
        if(lableService.addLable(lable) == 1){
           return DtoUtil.returnSuccess("新增个人标签成功");
        }else {
            return DtoUtil.returnFail("失败","9999");
        }
    }

}
