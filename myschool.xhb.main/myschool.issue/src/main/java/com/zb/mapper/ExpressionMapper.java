package com.zb.mapper;

import com.zb.entity.Expression;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExpressionMapper {
    //获取全部表情信息
    List<Expression>getExpressionAll();

    //根据表情编号获取表情信息
    Expression getExpressionById(@Param("expressionId")Integer expressionId);
}
