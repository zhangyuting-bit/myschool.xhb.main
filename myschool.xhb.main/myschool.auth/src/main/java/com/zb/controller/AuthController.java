package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.entity.AuthToken;
import com.zb.pojo.XcUserExt;
import com.zb.service.AuthService;
import com.zb.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {
    @Autowired(required = false)
    private AuthService authService;
    @Autowired(required = false)
    private JwtService jwtService;

    @PostMapping("/userlogin")
    public Dto<AuthToken> userlogin(String username , String password){
        return authService.userlogin(username,password);
    }
    @GetMapping("/userJwt")
    public Dto<XcUserExt> userJwt(@RequestParam("token") String token){
        return authService.getUserJwt(token);
    }

    @GetMapping("userOut")
    public Dto userOut(String token){
        return authService.deleteUserToken(token);
    }

    @GetMapping("changetoken")
    public Dto<String> changetoken(String token){
        return authService.changetoken(token);
    }

    @RequestMapping("/getXcUserExtByUsername")
    public XcUserExt getXcUserExtByUsername(@RequestParam(value = "name") String name){
        XcUserExt show = authService.show(name);
        return show;
    }

    @RequestMapping("/createToken")
    public String createToken(@RequestParam(value = "username") String username)throws Exception{
        String token = jwtService.createToken(username);
        return token;
    }
}
