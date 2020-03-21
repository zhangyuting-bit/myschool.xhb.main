package com.zb.service;

import com.zb.dto.Dto;
import com.zb.pojo.TeacherInfo;
import com.zb.pojo.TeacherJob;
import com.zb.pojo.UserInfo;
import com.zb.pojo.XcUserExt;

import java.util.List;

public interface UserService {
    //用户登陆
    public Dto register(String username,String random,String writeRandom,String password,String repassword,Integer codeType);
    //申请教师邀请码
    public Dto getInviteCode(String teacherName, String phone, String schoolname,String inviteCode);
    //修改（身份编号）如果是教师身份在添加教师信息
    public Dto updateCodeType(String token,Integer codeType);
    //修改称呼
    Dto updateName(String id,String name);
    //修改所在学校
    Dto updateSchoolname(String id,String schoolname);
    //修改密码
    Dto updatePassword(String id,String oldPassword,String twoPassword,String newPassward);
    //根据手机号查询用户
    public UserInfo getUserInfoByUserName(String username);
    //找回密码
    public Dto retrievePassword(String username ,String password,String random,String writeRandom);
    //新增工作时间
    public Dto addTeacherJob(List<Integer> jobday,TeacherJob teacherJob);
    //根据用户名（手机号）查询用户信息
    public XcUserExt getXcUserExtByUsername(String username) ;
    //修改用户头像
    public Dto updateUserPic(String id,String userpic);
    //根据token查询用户信息
    public Dto getUserInfoByToken(String token);

}
