package com.zb.register.controller;

import java.util.HashMap;
import java.util.Set;

import com.zb.register.service.CCPRestSDK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendSmsController {

    @Autowired
    private CCPRestSDK restAPI;

    @RequestMapping("/sendSms")
    public int show(@RequestParam(value = "phone",required = true) String phone){
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
            return 1;
        }else{
            System.out.println("状态码：" + result.get("statusCode") +" 发送信息= "+result.get("statusMsg"));
            return 0;
        }
    }

}
