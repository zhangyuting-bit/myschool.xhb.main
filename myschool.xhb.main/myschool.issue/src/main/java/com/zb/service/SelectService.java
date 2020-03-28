package com.zb.service;

import com.zb.entity.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SelectService {
    //添加新题目
    public Select addSelect(Select select);
}
