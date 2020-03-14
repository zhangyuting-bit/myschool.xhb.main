package com.zb.service.impl;

import com.zb.entity.AddExpression;
import com.zb.mapper.AddExpressionMapper;
import com.zb.service.AddExpressionService;
import com.zb.util.IdWorker;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class AddExpressionServiceImpl implements AddExpressionService {
    @Resource
    private AddExpressionMapper addExpressionMapper;

    @Override
    public Boolean addAddExpression(AddExpression addExpression) {
        addExpression.setId(IdWorker.getId());
        return addExpressionMapper.addAddExpression(addExpression)>0;
    }
}
