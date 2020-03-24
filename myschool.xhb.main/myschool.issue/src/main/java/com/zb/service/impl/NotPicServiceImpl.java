package com.zb.service.impl;

import com.zb.entity.NotPic;
import com.zb.mapper.NotPicMapper;
import com.zb.service.NotPicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class NotPicServiceImpl implements NotPicService {
    @Resource
    private NotPicMapper notPicMapper;

    //添加图片
    @Override
    public Integer addNotPic(NotPic notPic) {
        return notPicMapper.addNotPic(notPic);
    }

    //根据通知编号查询图片
    @Override
    public List<NotPic> getPicByFId(String functionId) {
        return notPicMapper.getPicByFId(functionId);
    }

    //根据通知编号查询状态为0的图片
    @Override
    public NotPic getPicByStatu(String functionId) {
        return notPicMapper.getPicByStatu(functionId);
    }
}
