package com.zb.feign;

import com.zb.entity.Notification;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "NOTIFICATIONSERVER")
public interface NotificationCollectFeign {
    //根据分数编号获取集合成绩单例
    @GetMapping("/getNotificationByNotId/{notificationId}")
    public Notification getNotificationByNotId(@PathVariable("notificationId")String notificationId);
}
