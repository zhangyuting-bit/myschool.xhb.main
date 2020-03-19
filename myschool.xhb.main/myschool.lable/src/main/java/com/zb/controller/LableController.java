package com.zb.controller;

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

}
