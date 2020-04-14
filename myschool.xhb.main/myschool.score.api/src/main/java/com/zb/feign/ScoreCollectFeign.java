package com.zb.feign;

import com.zb.entity.Score;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "SCORESERVER")
public interface ScoreCollectFeign {
    //根据分数编号获取集合成绩单例
    @GetMapping("/getByScoreId/{scoreId}")
    public Score getByScoreId(@PathVariable("scoreId")String scoreId);
}
