package com.zb.feign;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.pojo.Class_add;
import com.zb.pojo.Class_info;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "CLASSSERVICE")
public interface ClassMassagesFeign {
    //根据id获取班级的信息
    @GetMapping("/showclassid/{class_number}")
    public Class_add showclass(@PathVariable(value = "class_number")String class_number);

    //根据用户的id获取该用户所在的所有班级信息
    @GetMapping("/userclassinfo/{user_id}")
    public List<Class_info> showclassid(@PathVariable(value = "user_id")Integer user_id);

    //获取班级内部人员的信息
    @GetMapping("/classinfo/{class_number}")
    public List<Class_info> classinfo(@PathVariable("class_number")Integer class_number);
}
