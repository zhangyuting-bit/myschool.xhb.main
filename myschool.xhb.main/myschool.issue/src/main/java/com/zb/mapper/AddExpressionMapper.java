package com.zb.mapper;

import com.zb.entity.AddExpression;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AddExpressionMapper {
    List<AddExpression>getAddExpressionByNId(@Param("functionId")Integer functionId);
}
