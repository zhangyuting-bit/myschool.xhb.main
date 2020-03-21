package com.zb.service.impl;

import com.zb.mapper.RoleMapper;
import com.zb.pojo.Role;
import com.zb.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired(required = false)
    private RoleMapper roleMapper;
    @Override
    public List<Role> getRoleList() {
        List<Role> roleList = roleMapper.getRoleList();
        return roleList;
    }
}
