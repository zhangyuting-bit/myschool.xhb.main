package com.zb.mapper;

import com.zb.pojo.TeacherInfo;
import com.zb.pojo.TeacherJob;
import com.zb.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    //根据用户名（手机号）查询用户
    public UserInfo getUserInfoByUserName(@Param("username") String username);
    //根据手机号查询教师信息
    public TeacherInfo getTeacherInfo(@Param("phone") String phone);
    //新增用户
    int addUser(UserInfo userInfo);
    //新增教师
    int addTeacher(TeacherInfo teacherInfo);
    //修改用户信息
    int updateUserInfo(UserInfo userInfo);
    //修改教师表信息
    int updateTeacherInfo(TeacherInfo teacherInfo);
    //根据用户编号查询用户信息
    UserInfo getUserInfoById(@Param("id") String id);
    //新增工作时间
    int addTeacherJob(TeacherJob teacherJob);
}
