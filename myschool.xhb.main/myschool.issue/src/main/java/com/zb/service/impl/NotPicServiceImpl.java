package com.zb.service.impl;

import com.zb.entity.NotPic;
import com.zb.mapper.NotPicMapper;
import com.zb.service.NotPicService;

import javax.annotation.Resource;

public class NotPicServiceImpl implements NotPicService {
    @Resource
    private NotPicMapper notPicMapper;

    //添加图片
    @Override
    public Integer addNotPic(NotPic notPic) {
        return notPicMapper.addNotPic(notPic);
    }
}
