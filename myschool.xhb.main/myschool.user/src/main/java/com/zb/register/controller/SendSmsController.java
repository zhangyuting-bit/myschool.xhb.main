package com.zb.register.controller;

import java.util.HashMap;
import java.util.Set;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.register.service.CCPRestSDK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendSmsController {

    @Autowired
    private CCPRestSDK restAPI;

    /**
     * 获取验证码
     * @param username
     * @return 发送的验证码
     */
    @RequestMapping("/sendSms")
    public Dto show(@RequestParam(value = "username",required = true) String username){
        HashMap<String, Object> result = null;
        restAPI.init("app.cloopen.com", "8883");
        restAPI.setAccount("8a216da86f17653b016f4612998122bb", "c24d6c51aa10429baa5ead2330bb4180");
        restAPI.setAppId("8a216da86f17653b016f461299eb22c1");
        int randomNum=(int)((Math.random()*9+1)*100000);
        String random = String.valueOf(randomNum);
        result = restAPI.sendTemplateSMS(username,"1" ,new String[]{random,"1"});

        System.out.println("SDKTestSendTemplateSMS result=" + result);

        if("000000".equals(result.get("statusCode"))){
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for(String key:keySet){
                Object object = data.get(key);
                System.out.println(key +" = "+object);
            }

            //将验证码存入redis:格式 key 是电话号码=>value 是验证码
            return DtoUtil.returnSuccess("1",random);
        }else{
            System.out.println("状态码：" + result.get("statusCode") +" 发送信息= "+result.get("statusMsg"));
            return DtoUtil.returnFail("发送失败","0000");
        }
    }

}
