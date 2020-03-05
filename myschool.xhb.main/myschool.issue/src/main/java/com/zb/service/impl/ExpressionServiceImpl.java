package com.zb.service.impl;

import com.zb.entity.Expression;
import com.zb.mapper.ExpressionMapper;
import com.zb.service.ExpressionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ExpressionServiceImpl implements ExpressionService {
    @Resource
    private ExpressionMapper expressionMapper;

    //获取全部表情信息
    @Override
    public List<Expression> getExpressionAll() {
        return expressionMapper.getExpressionAll();
    }
}
