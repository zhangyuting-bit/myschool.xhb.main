package com.zb.mapper;

import com.zb.pojo.Role;
import com.zb.pojo.XuRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
    public Role getRoleByUserId(@Param("userid") String userid);
    public List<Role> getRoleList();
    //新增用户和角色关系表
    public int addXuRole(XuRole xuRole);
    //根据角色编号查询角色
    public Role getRoleByRoleCode(@Param("role_code") Integer role_code);
}
