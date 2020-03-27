package com.zb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class MyEurekaServerApp {
    //mc_dev添加了 一段代码
    public static void main(String[] args) {
        SpringApplication.run(MyEurekaServerApp.class,args);
    }
}
