package com.zb.mapper;

import com.zb.entity.Collect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CollectMapper {
    //添加收藏
    Integer addCollect(Collect collect);

    //删除收藏
    Integer delCollect(@Param("typeId")String typeId,@Param("userId")String userId);

    //根据用户编号获取收藏信息
    List<Collect>getCollectByUserId(@Param("userId")String userId);
}
