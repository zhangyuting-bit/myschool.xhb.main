package com.zb.service;

import com.zb.entity.NotPic;

import java.util.List;

public interface NotPicService {
    //添加图片
    public Integer addNotPic(NotPic notPic);

    //根据通知编号查询图片
    public List<NotPic> getPicByFId(String functionId);

    //根据通知编号查询状态为0的图片
    public NotPic getPicByStatu(String functionId);
}
