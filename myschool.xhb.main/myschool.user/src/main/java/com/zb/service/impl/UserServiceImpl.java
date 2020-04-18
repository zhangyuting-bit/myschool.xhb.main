package com.zb.service.impl;

import com.zb.config.RabbitConfig;
import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.feign.AuthFeignClient;
import com.zb.mapper.UserMapper;
import com.zb.mapper.RoleMapper;
import com.zb.pojo.*;
import com.zb.register.service.CCPRestSDK;
import com.zb.service.UserService;
import com.zb.util.IdWorker;
import com.zb.vo.SendVo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private CCPRestSDK restAPI;

    @Autowired(required = false)
    private UserMapper userMapper;
    @Autowired(required = false)
    private RoleMapper xcRoleMapper;
    @Autowired
    private AuthFeignClient authFeignClient;
    @Override
    public Dto register(String username,String random,String writeRandom,String password,String repassword,Integer codeType) {
        //验证码输入正确
        if (writeRandom.equals(random)) {
            UserInfo user = userMapper.getUserInfoByUserName(username);
            UserInfo loginUser = null;
            if(!password.equals(repassword)){
                return DtoUtil.returnFail("两次密码不一致","001");
            }
            if (user == null) {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String pwd = passwordEncoder.encode(password);
                //没有该用户，先注册，再登陆
                loginUser = new UserInfo();
                loginUser.setId(IdWorker.getId());
                loginUser.setPhone(username);
                loginUser.setUsername(username);
                loginUser.setPassword(pwd);
                loginUser.setCodeType(codeType);
                int i = userMapper.addUser(loginUser);
                if(i>0){
                    if(codeType==1||codeType.equals(1)){
                        System.out.println("添加教师！");
                        TeacherInfo teacherInfo=new TeacherInfo();
                        teacherInfo.setId(IdWorker.getId());
                        teacherInfo.setPhone(username);
                        teacherInfo.setUserId(loginUser.getId());
                        int i1 = userMapper.addTeacher(teacherInfo);
                        System.out.println("添加教师结果：--------"+i1);
                    }
                    //添加用户角色关系表
                    XuRole xuRole=new XuRole();
                    xuRole.setId(IdWorker.getId());
                    xuRole.setUser_id(loginUser.getId());
                    Role role = xcRoleMapper.getRoleByRoleCode(codeType);
                    xuRole.setRole_id(role.getId());
                    xuRole.setCreator(role.getRole_name());
                    int i1 = xcRoleMapper.addXuRole(xuRole);
                }
                return DtoUtil.returnSuccess("ok",loginUser);
            }else{
                //登陆
                return DtoUtil.returnSuccess("该用户已存在，可进行登陆！",user);
            }
        }else{
            return DtoUtil.returnFail("验证码输入错误","0000");
        }
    }

    @Override
    public Dto getInviteCode(String teacherName, String phone, String schoolname,String inviteCode) {

        TeacherInfo teacher = userMapper.getTeacherInfo(phone);
        if(teacher!=null){
            TeacherInfo teacherInfo=new TeacherInfo();
            teacherInfo.setUserId(teacher.getUserId());
            teacherInfo.setTeacherName(teacherName);
            teacherInfo.setSchoolname(schoolname);
            teacherInfo.setInviteCode(inviteCode);
            int i = userMapper.updateTeacherInfo(teacherInfo);
            if(i>0){
                return DtoUtil.returnSuccess("ok",i);
            }else{
                return DtoUtil.returnFail("获取失败","001");
            }
        }else{
            return DtoUtil.returnFail("没有此账号","001");
        }

    }

    @Override
    public Dto updateCodeType(String token,Integer codeType) {
        Dto<XcUserExt> xcUserExtDto = authFeignClient.userJwt(token);
        if(xcUserExtDto.getData()==null){
            return DtoUtil.returnFail("用户未登录！","000");
        }
        String id = xcUserExtDto.getData().getId();
        UserInfo user = userMapper.getUserInfoById(id);
        UserInfo userInfo=new UserInfo();
        userInfo.setId(id);
        userInfo.setCodeType(codeType);
        int i = userMapper.updateUserInfo(userInfo);
        //选的身份是教师
        if(i>0){
            //添加教师信息
            if(codeType==1){
                TeacherInfo teacherInfo=new TeacherInfo();
                teacherInfo.setId(IdWorker.getId());
                teacherInfo.setUserId(user.getId());
                teacherInfo.setPhone(user.getPhone());
                int i1 = userMapper.updateTeacherInfo(teacherInfo);
            }
            return DtoUtil.returnSuccess("ok",i);
        }else{
            return DtoUtil.returnFail("修改错误","009");
        }
    }

    @Override
    public Dto updateName(String id,String name) {
        UserInfo userInfo=new UserInfo();
        userInfo.setId(id);
        userInfo.setName(name);
        UserInfo user = userMapper.getUserInfoById(id);
        //如果该用户是教师
        if(user.getCodeType()==1){
            //同时修改教师表
            TeacherInfo teacherInfo=new TeacherInfo();
            teacherInfo.setUserId(id);
            teacherInfo.setTeacherName(name);
            int i = userMapper.updateTeacherInfo(teacherInfo);
            if(i>0){
                int i2=userMapper.updateUserInfo(userInfo);
                if(i2>0){
                    return DtoUtil.returnSuccess("ok",i2);
                }else{
                    return DtoUtil.returnFail("修改失败","009");
                }
            }else{
                return DtoUtil.returnFail("修改失败","009");
            }
            //不是教师
        }else{
            int i3=userMapper.updateUserInfo(userInfo);
            if(i3>0){
                return DtoUtil.returnSuccess("ok",i3);
            }else{
                return DtoUtil.returnFail("修改失败","009");
            }
        }
    }

    @Override
    public Dto updateSchoolname(String id,String schoolname) {
        UserInfo userInfo=new UserInfo();
        userInfo.setId(id);
        userInfo.setSchoolname(schoolname);
        UserInfo user = userMapper.getUserInfoById(id);
        //如果该用户是教师
        if(user.getCodeType()==1){
            //同时修改教师表
            TeacherInfo teacherInfo=new TeacherInfo();
            teacherInfo.setUserId(id);
            teacherInfo.setSchoolname(schoolname);
            int i = userMapper.updateTeacherInfo(teacherInfo);
            if(i>0){
                int i2=userMapper.updateUserInfo(userInfo);
                if(i2>0){
                    return DtoUtil.returnSuccess("ok",i2);
                }else{
                    return DtoUtil.returnFail("修改失败","009");
                }
            }else{
                return DtoUtil.returnFail("修改失败","009");
            }
            //不是教师
        }else{
            int i3=userMapper.updateUserInfo(userInfo);
            if(i3>0){
                return DtoUtil.returnSuccess("ok",i3);
            }else{
                return DtoUtil.returnFail("修改失败","009");
            }
        }
    }

    @Override
    public Dto updatePassword(String id,String oldPassword,String twoPassword,String newPassward) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String oldPassword1 = passwordEncoder.encode(oldPassword);
        String newPassward1 = passwordEncoder.encode(newPassward);
        boolean matches = passwordEncoder.matches(oldPassword, oldPassword1);
        UserInfo user = userMapper.getUserInfoById(id);
        if(matches){
            boolean matches1 = passwordEncoder.matches(twoPassword, oldPassword1);
            if(matches1){
                boolean matches2 = passwordEncoder.matches(oldPassword,user.getPassword());
                if(matches2){
                    UserInfo userInfo=new UserInfo();
                    userInfo.setId(id);
                    userInfo.setPassword(newPassward1);
                    int i=userMapper.updateUserInfo(userInfo);
                    if(i>0){
                        return DtoUtil.returnSuccess("ok",i);
                    }else{
                        return DtoUtil.returnFail("修改失败","009");
                    }
                }else{
                    return DtoUtil.returnFail("原密码输入错误","008");
                }
            }
        }
        return DtoUtil.returnFail("原密码输入错误","008");
    }

    @Override
    public UserInfo getUserInfoByUserName(String username) {
        return userMapper.getUserInfoByUserName(username);
    }

    @Override
    public Dto retrievePassword(String username, String password,String random,String writeRandom) {
        if (writeRandom.equals(random)) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String password1 = bCryptPasswordEncoder.encode(password);
            UserInfo user = userMapper.getUserInfoByUserName(username);
            if (user != null) {
                UserInfo userInfo = new UserInfo();
                userInfo.setId(user.getId());
                userInfo.setPassword(password1);
                int i = userMapper.updateUserInfo(userInfo);
                return DtoUtil.returnSuccess("ok", i);
            } else {
                return DtoUtil.returnFail("手机号输入错误", "007");
            }
        }else{
            return DtoUtil.returnFail("验证码输入错误！", "007");
        }
    }

    @Override
    public Dto addTeacherJob(List<Integer> jobday, TeacherJob teacherJob) {
        TeacherJob teacherJob1=null;
        int i=0;
        for (int job : jobday) {
            teacherJob1.setJobId(IdWorker.getId());
            teacherJob1.setTeacherId(IdWorker.getId());
            teacherJob1.setAmhourstart(teacherJob.getAmhourstart());
            teacherJob1.setAmminstart(teacherJob.getAmminstart());
            teacherJob1.setAmhourend(teacherJob.getAmhourend());
            teacherJob1.setAmminend(teacherJob.getAmminend());
            teacherJob1.setPmhourstart(teacherJob.getPmhourstart());
            teacherJob1.setPmminstart(teacherJob.getPmminstart());
            teacherJob1.setPmhourend(teacherJob.getPmhourend());
            teacherJob1.setPmminend(teacherJob.getPmminend());
            teacherJob1.setTeacherId(teacherJob.getTeacherId());
            teacherJob1.setJobday(job);
            i = userMapper.addTeacherJob(teacherJob);
        }
        return DtoUtil.returnSuccess("ok",i);
    }


    @Override
    public XcUserExt getXcUserExtByUsername(String username) {
        System.out.println("getXcUserExtByUsername:------"+username);
        //根据用户名称查询用户信息
         UserInfo userInfoByUserName = userMapper.getUserInfoByUserName(username);
        System.out.println("====================================="+userInfoByUserName.getId());
        Role role = xcRoleMapper.getRoleByUserId(userInfoByUserName.getId());
          //创建XcUserExt对象
        XcUserExt xcUserExt=new XcUserExt();
        //将userByusername属性拷贝到xcUserExt中，两个类型中的属性名称一模一样的才好拷贝
        BeanUtils.copyProperties(userInfoByUserName,xcUserExt);
         return xcUserExt;
    }

    @Override
    public Dto updateUserPic(String id, String userpic) {
        UserInfo userInfo=new UserInfo();
        userInfo.setId(id);
        userInfo.setUserpic(userpic);
        int i = userMapper.updateUserInfo(userInfo);
        if(i>0){
            return DtoUtil.returnSuccess("ok",i);
        }else{
            return DtoUtil.returnFail("修改失败","005");
        }
    }

    /**
     * 获取用户信息
     * @param token
     * @return
     */
    public UserInfo getUserInfo(String token){
        Dto<XcUserExt> xcUserExtDto = authFeignClient.userJwt(token);
        String id = xcUserExtDto.getData().getId();
        UserInfo userinfo = userMapper.getUserInfoById(id);
        return userinfo;
    }

    @Override
    public UserInfo getUserInfoByToken(String token) {
        Dto<XcUserExt> xcUserExtDto = authFeignClient.userJwt(token);
        String id = xcUserExtDto.getData().getId();
        UserInfo userinfo = userMapper.getUserInfoById(id);
        return userinfo;
    }

    @Override
    public UserInfo getUserInfoById(String id) {
        return userMapper.getUserInfoById(id);
    }

    @Override
    public TeacherInfo getTeacherInfoById(String id) {
        return userMapper.getTeacherInfoById(id);
    }

    //监听定时发信息队列
    @RabbitListener(queues = RabbitConfig.sendQueue)
    public void sendMsg(SendVo sendVo){
        UserInfo userInfo=userMapper.getUserInfoById(sendVo.getUserId());
        HashMap<String, Object> result = null;
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount("8a216da86f17653b016f4612998122bb", "c24d6c51aa10429baa5ead2330bb4180");
        restAPI.setAppId("8a216da86f17653b016f461299eb22c1");
        result = restAPI.sendTemplateSMS(userInfo.getPhone(),"1" ,new String[]{sendVo.getClassName()+"班的老师"+sendVo.getTeacherName()+"提醒您今日还未进行习惯打卡","5"});
        if("000000".equals(result.get("statusCode"))){
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
        }else{
            System.out.println("状态码：" + result.get("statusCode") +" 发送信息= "+result.get("statusMsg"));
        }
    }
}
