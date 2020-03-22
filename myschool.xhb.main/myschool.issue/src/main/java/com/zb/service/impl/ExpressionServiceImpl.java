package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.entity.Expression;
import com.zb.mapper.ExpressionMapper;
import com.zb.service.ExpressionService;
import com.zb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpressionServiceImpl implements ExpressionService {
    @Resource
    private ExpressionMapper expressionMapper;

    @Autowired
    private RedisUtil redisUtil;

    //获取全部表情信息
    @Override
    public List<Expression> getExpressionAll() {
        return expressionMapper.getExpressionAll();
    }

    //根据表情编号获取表情信息
    @Override
    @Cacheable(value = "cache" ,key="#expressionId")
    public Expression getExpressionById(Integer expressionId) {
        Expression expression=null;
        String key="expression:"+expressionId;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            expression=JSON.parseObject(o.toString(),Expression.class);
        }else {
            expression=expressionMapper.getExpressionById(expressionId);
            redisUtil.set(key, JSON.toJSONString(expression),12000);
        }
        return expression;
    }


}
