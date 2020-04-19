package com.zb.feign;

import com.zb.dto.Dto;
import com.zb.entity.NotOne;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "ISSUESERVER")
public interface NotOneFeign {
    //根据班级编号和通知类型编号获取全部对应通知
    @GetMapping("/getNotOneByUserId")
    public List<NotOne> getOneByUserId(@RequestParam(value = "typeId",required = false) Integer typeId
            ,@RequestParam(value = "userId",required = false)String userId);

    //根据用户编号和通知编号删除通知信息
    @GetMapping("/delNotOne")
    public Integer delNotOneByNotIdAndUserId(@RequestParam(value = "functionId",required = false)String functionId
            ,@RequestParam(value = "typeId",required = false) Integer typeId);

    //添加个人信息
    @RequestMapping("/addNotOne")
    public Integer addNotOne(@RequestBody NotOne notOne);

}
