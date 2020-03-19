package com.zb.service.impl;

import com.zb.mapper.LableMapper;
import com.zb.pojo.Lable;
import com.zb.service.LableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LableServiceImpl implements LableService {

    @Autowired(required = false)
    LableMapper lableMapper;

    @Override
    public List<Lable> listAllPublicLables() {
        return lableMapper.listAllPublicLables();
    }

    @Override
    public List<Lable> listPersonalLables(String userId) {
        return lableMapper.listPersonalLables(userId);
    }

    @Override
    public int addLable(Lable lable) {
        return lableMapper.addLable(lable);
    }
}
