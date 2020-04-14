package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.Collect;
import com.zb.service.CollectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class CollectController {
    @Resource
    private CollectService collectService;

    //添加收藏
    @RequestMapping("/addCollect")
    public Dto<Integer> addCollect(Collect collect){
        return DtoUtil.returnSuccess("ok",collectService.addCollect(collect));
    }

    //删除收藏
    @GetMapping("/delCollect")
    public Dto<Integer> delCollect(String typeId, String userId){
        return DtoUtil.returnSuccess("ok",collectService.delCollect(typeId, userId));
    }

    //根据用户编号获取收藏信息
    @GetMapping("/getCollectByUserId/{userId}")
    public Dto<List<Collect>> getCollectByUserId(@PathVariable("userId") String userId){
        return DtoUtil.returnSuccess("ok",collectService.getCollectByUserId(userId));
    }

}
