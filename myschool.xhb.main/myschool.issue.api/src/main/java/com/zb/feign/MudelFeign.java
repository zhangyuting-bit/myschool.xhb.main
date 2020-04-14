package com.zb.feign;

import com.zb.dto.Dto;
import com.zb.entity.Mudel;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "ISSUESERVER")
public interface MudelFeign {
    //根据功能类型编号获取模板
    @GetMapping("/getMudelByTypeId/{typeId}")
    public Dto<List<Mudel>> getMudelByTypeId(@PathVariable("typeId") Integer typeId);

    // 根据模板编号获取模板信息
    @GetMapping("/getMudelById/{mudelId}")
    public Dto<Mudel> getMudelById(@PathVariable("mudelId")Integer mudelId);
}
