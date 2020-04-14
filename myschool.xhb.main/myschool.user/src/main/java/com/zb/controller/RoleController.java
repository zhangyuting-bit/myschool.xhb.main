package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.pojo.Role;
import com.zb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/getRoleList")
    public Dto getRoleList(){
        List<Role> roleList = roleService.getRoleList();
        return DtoUtil.returnSuccess("ok",roleList);
    }
}
