package com.zb.service.impl;

import com.zb.entity.AddExpression;
import com.zb.entity.Expression;
import com.zb.mapper.AddExpressionMapper;
import com.zb.mapper.ExpressionMapper;
import com.zb.service.ExpressionService;
import com.zb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpressionServiceImpl implements ExpressionService {
    @Resource
    private ExpressionMapper expressionMapper;

    @Resource
    private AddExpressionMapper addExpressionMapper;

    @Autowired
    private RedisUtil redisUtil;

    //获取全部表情信息
    @Override
    public List<Expression> getExpressionAll() {
        return expressionMapper.getExpressionAll();
    }

    //根据通知编号获取表情信息
    @Override
    public List<Expression> getExpressionByNId(String functionId) {
        List<AddExpression>list=addExpressionMapper.getAddExpressionByNId(functionId);
        List<Expression>list1=new ArrayList<>();
        for (AddExpression addExpression:list) {
            Expression expression=getExpressionById(addExpression.getExpressionId());
            list1.add(expression);
        }
        return list1;
    }

    //根据表情编号获取表情信息
    @Override
    @Cacheable(value = "cache" ,key="#expressionId")
    public Expression getExpressionById(Integer expressionId) {
        Expression expression=null;
        String key="expression:"+expressionId;
        if (redisUtil.hasKey(key)){
            Map<Object, Object> hmget = redisUtil.hmget(key);
            expression.setExpressionId(Integer.parseInt(hmget.get("expressionId").toString()));
            expression.setExpressionSrc(hmget.get("expressionSrc").toString());
        }else {
            Map<String,Object>map=new HashMap<>();
            expression=expressionMapper.getExpressionById(expressionId);
            map.put("expressionId",expression.getExpressionId());
            map.put("expressionSrc",expression.getExpressionSrc());
            redisUtil.hmset(key,map);
        }
        return expression;
    }


}
