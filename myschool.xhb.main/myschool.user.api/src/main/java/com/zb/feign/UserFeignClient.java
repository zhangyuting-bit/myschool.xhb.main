package com.zb.feign;

import com.zb.dto.Dto;
import com.zb.pojo.TeacherInfo;
import com.zb.pojo.UserInfo;
import com.zb.pojo.XcUserExt;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "USERSERVICE")
public interface UserFeignClient {
    @RequestMapping("/getXcUserExtByUsername")
    public XcUserExt getXcUserExtByUsername(@RequestParam(value = "username") String username);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    @RequestMapping("/getUserInfoByToken")
    public UserInfo getUserInfoByToken(@RequestParam(value = "token") String token);


    /**
     * 根据用户编号获取用户信息
     * @param id
     * @return
     */
    @RequestMapping("/getUserInfoById")
    public UserInfo getUserInfoById(@RequestParam(value = "id") String id);

    /**
     * 根据教师编号获取教师信息
     * @param id
     * @return
     */
    @RequestMapping("/getTeacherInfoById")
    public TeacherInfo getTeacherInfoById(@RequestParam(value = "id") String id);
}
