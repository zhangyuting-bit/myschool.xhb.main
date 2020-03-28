package com.zb.service;

import com.zb.entity.Expression;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpressionService {
    //获取全部表情信息
    public List<Expression> getExpressionAll();

    //根据表情编号获取表情信息
    public Expression getExpressionById(Integer expressionId);
}
