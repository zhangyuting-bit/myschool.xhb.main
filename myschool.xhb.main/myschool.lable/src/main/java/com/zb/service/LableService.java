package com.zb.service;

import com.zb.pojo.Lable;

import java.util.List;

public interface LableService {
    List<Lable> listAllPublicLables();
    List<Lable> listPersonalLables(String userId);
    int addLable(Lable lable);
    int delectLable(Integer lableId);
}
