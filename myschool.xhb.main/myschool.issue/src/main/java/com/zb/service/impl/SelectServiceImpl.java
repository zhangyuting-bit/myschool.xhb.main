package com.zb.service.impl;

import com.zb.entity.Select;
import com.zb.mapper.SelectMapper;
import com.zb.service.SelectService;
import com.zb.util.IdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
@Service
public class SelectServiceImpl implements SelectService {
    @Resource
    private SelectMapper selectMapper;

    //添加新题目
    @Override
    public Select addSelect(Select select) {
        select.setSelectId(IdWorker.getId());
        selectMapper.addSelect(select);
        return select;
    }
}
