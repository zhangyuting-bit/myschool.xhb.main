package com.zb.service;

import com.zb.entity.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectService {
    //添加收藏
    Integer addCollect(Collect collect);

    //删除收藏
    Integer delCollect(String typeId, String userId);

    //根据用户编号获取收藏信息
    List<Collect> getCollectByUserId(String userId);
}
