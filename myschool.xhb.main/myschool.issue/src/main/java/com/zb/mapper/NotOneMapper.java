package com.zb.mapper;

import com.zb.entity.NotOne;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotOneMapper {
    //添加通知用户信息
    Integer addOne(NotOne notOne);

    //根据用户编号获取所有信息
    List<NotOne> getOneByUserId(@Param("typeId")Integer typeId,@Param("userId")String userId);

    //根据消息类型和编号获取个人信息
    List<NotOne> getOneByUser(@Param("userId")String userId);

    //根据类型编号和通知编号删除通知信息
    Integer delNotOneByNotIdAndUserId(@Param("functionId")String functionId
            ,@Param("typeId")Integer typeId);

    //根据通知编号删除个人信息
    Integer delNotOneByNotId(@Param("userId")String userId,@Param("functionId")String functionId
            ,@Param("typeId")Integer typeId);

    //根据班级编号和用户编号删除notOne表里的个人信息
    Integer delNotOneByGradeIdAndUserId(@Param("gradeId")String gradeId,@Param("userId")String userId);
}
