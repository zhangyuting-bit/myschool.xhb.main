package com.zb.mapper;

import com.zb.entity.NotOne;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotOneMapper {
    //添加通知用户信息
    Integer addOne(NotOne notOne);

    //根据用户编号获取所有信息
    List<NotOne> getOneByUserId(@Param("typeId")Integer typeId,@Param("userId")String userId);
}
