package com.zb.feign;

import com.zb.dto.Dto;
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
    public Dto getUserInfoByToken(@RequestParam(value = "token") String token);
}
