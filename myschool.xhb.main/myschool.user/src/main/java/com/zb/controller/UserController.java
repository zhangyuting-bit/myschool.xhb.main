package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.pojo.TeacherJob;
import com.zb.pojo.UserInfo;
import com.zb.pojo.XcUserExt;
import com.zb.register.service.CCPRestSDK;
import com.zb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CCPRestSDK restAPI;

    /**
     * 注册
     * @param username
     * @param writeRandom 用户输入的验证码
     * @return
     */
    @RequestMapping("/register")
    public Dto register(String username,String random,String writeRandom,String password,String repassword,Integer codeType) {
        Dto dto = userService.register(username, random, writeRandom, password, repassword, codeType);
        return dto;
    }

    /**
     * 修改用户名
     * @param id
     * @param name
     * @return
     */
    @RequestMapping("/updateName")
    public Dto updateName(@RequestParam(value = "id") String id,@RequestParam("name") String name){
        Dto dto = userService.updateName(id, name);
        return dto;
    }

    /**
     * 修改学校名称
     * @param id
     * @param schoolname
     * @return
     */
    @RequestMapping("/updateSchoolName")
    public Dto updateSchoolName(@RequestParam(value = "id") String id,@RequestParam("schoolname") String schoolname){
        Dto dto = userService.updateSchoolname(id,schoolname);
        return dto;
    }

    /**
     * 修改密码
     * @param id
     * @param oldPassword
     * @param twoPassword
     * @param newPassward
     * @return
     */
    @RequestMapping("/updatePassword")
    public Dto updatePassword(@RequestParam(value = "id") String id,
                              @RequestParam("oldPassword") String oldPassword,
                              @RequestParam("twoPassword") String twoPassword,
                              @RequestParam("newPassward") String newPassward){
        Dto dto = userService.updatePassword(id,oldPassword,twoPassword,newPassward);
        return dto;
    }

    /**
     * 修改用户类型
     * @param token
     * @param codeType
     * @return
     */
    @RequestMapping("/updateCodeType")
    public Dto updateCodeType(@RequestParam("token") String token,@RequestParam("codeType") Integer codeType) {
        Dto dto = userService.updateCodeType(token, codeType);
        return dto;
    }

    /**
     * 根据用户名获得用户信息
     * @param username
     * @return
     */
    @RequestMapping("/getUserInfoByUserName")
    public Dto getUserInfoByUserName(String username){
        UserInfo user = userService.getUserInfoByUserName(username);
        return DtoUtil.returnSuccess("ok",user);
    }

    /**
     * 找回密码
     * @param username  手机号
     * @param password  密码
     * @return
     */
    @RequestMapping("/retrievePassword")
    public Dto retrievePassword(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("random") String random,@RequestParam("writeRandom") String writeRandom){
        Dto dto = userService.retrievePassword(username, password,random,writeRandom);
        return dto;
    }


    @RequestMapping("/addTeacherJob")
    public Dto addTeacherJob(List<Integer> jobday, TeacherJob teacherJob){
        Dto dto = userService.addTeacherJob(jobday,teacherJob);
        return dto;
    }

    /**
     * 根据username获取XcUserExt信息共auth模块使用
     * @param username
     * @return
     */
    @RequestMapping("/getXcUserExtByUsername")
    public XcUserExt getXcUserExtByUsername(@RequestParam(value = "username") String username){
        XcUserExt xcUserExtByUsername = userService.getXcUserExtByUsername(username);
        return xcUserExtByUsername;
    }

    /**
     * 生成教师邀请码
     * @param teacherName
     * @param phone
     * @param schoolname
     * @return
     */
    @RequestMapping("/getInviteCode")
    public Dto getInviteCode(@RequestParam("teacherName") String teacherName, @RequestParam("phone") String phone, @RequestParam("schoolname") String schoolname){
        Dto i=null;
        HashMap<String, Object> result = null;
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount("8a216da86f17653b016f4612998122bb", "c24d6c51aa10429baa5ead2330bb4180");
        restAPI.setAppId("8a216da86f17653b016f461299eb22c1");
        int randomNum=(int)((Math.random()*9+1)*100000);
        String random = String.valueOf(randomNum);
        result = restAPI.sendTemplateSMS(phone,"1" ,new String[]{random,"1"});
        System.out.println("SDKTestSendTemplateSMS result=" + result);
        if("000000".equals(result.get("statusCode"))){
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }
            //将验证码存入redis:格式 key 是电话号码=>value 是验证码
//            return DtoUtil.returnSuccess("1",random);
            i = userService.getInviteCode(teacherName, phone, schoolname, random);
            System.out.println("进入注册验证：-----------"+i);
        }
        System.out.println("验证码注册结果：-----------"+i);
        return i;
    }

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    @RequestMapping("/getUserInfoByToken")
    public Dto getUserInfoByToken(@RequestParam(value = "token") String token){
        Dto userInfoByToken = userService.getUserInfoByToken(token);
        return userInfoByToken;
    }

}
