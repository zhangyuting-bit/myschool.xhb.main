package com.zb.service;

import com.zb.entity.NotOne;
import com.zb.pojo.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotOneService {
    //根据用户编号获取用户所在所有班级
    public UserInfo getUserGrade(String userId);

    //根据用户编号获取用户所有信息
    public List<NotOne>getOneAll(String userId);

    //根据用户编号获取所有信息
    public List<NotOne> getOneByUserId(Integer typeId,String userId);

    //根据用户编号和通知编号删除通知信息
    public Integer delNotOneByNotIdAndUserId(String functionId,Integer typeId);

    //根据消息编号和用户编号和类型编号删除信息
    public Integer delNotOneByNotId(String functionId,String userId,Integer typeId);
}
