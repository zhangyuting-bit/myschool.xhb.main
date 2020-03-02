package com.zb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MySchoolUserApp {
    public static void main(String[] args) {
        SpringApplication.run(MySchoolUserApp.class,args);
    }
}
