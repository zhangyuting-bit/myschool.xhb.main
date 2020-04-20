package com.zb.feign;

import com.zb.dto.Dto;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "CLASSSERVICE")
public interface ClassMassagesFeign {
    //根据id获取班级的信息
    @GetMapping("/showclassid/{id}")
    public Dto showclassid(@PathVariable(value = "id")String id);
    //根据用户的id获取该用户所在的所有班级信息
    @GetMapping("/userclassinfo/{user_id}")
    public Dto showclassid(@PathVariable(value = "user_id")Integer user_id);
    //获取班级内部人员的信息
    @GetMapping("/classinfo/{class_number}")
    public Dto classinfo(@PathVariable("class_number")Integer class_number);
}
