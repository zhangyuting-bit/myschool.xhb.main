package com.zb.feign;

import com.zb.dto.Dto;
import com.zb.entity.Expression;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "ISSUESERVER")
public interface ExpressionFeign {
    //获取全部表情信息
    @GetMapping("/getExpressionAll")
    public Dto<List<Expression>> getExpressionAll();

    //根据表情编号获取表情信息
    @GetMapping("/getExpressionById/{expressionId}")
    public Dto<Expression> getExpressionById(@PathVariable("expressionId") Integer expressionId);
}
