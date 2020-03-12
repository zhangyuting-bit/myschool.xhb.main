package com.zb.mapper;

import com.zb.entity.AddExpression;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddExpressionMapper {
    //根据通知编号获取表情信息
    List<AddExpression>getAddExpressionByNId(@Param("functionId")String functionId);

    //添加添加表情信息
    Integer addAddExpression(AddExpression addExpression);
}
