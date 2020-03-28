package com.zb.feign;

import com.zb.dto.Dto;
import com.zb.pojo.XcUserExt;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("auth2.server")
public interface AuthFeignClient {
    @GetMapping("/userJwt")
    public Dto<XcUserExt> userJwt(@RequestParam("token") String token);

}
