package com.zb.mapper;

import com.zb.entity.Grade;
import com.zb.entity.NotOne;
import com.zb.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotOneMapper {
    //根据班级编号获取用户信息
    List<User>getUserByGradeId(@Param("gradeId")String gradeId);

    //根据用户编号获取班级信息
    List<Grade>getGradeByUserId(@Param("userId")String userId);

    //根据用户编号查询用户信息
    User getUserByUserId(@Param("userId")String userId);

    //添加通知用户信息
    Integer addOne(NotOne notOne);

    //根据用户编号获取所有信息
    List<NotOne> getOneByUserId(@Param("typeId")Integer typeId,@Param("userId")String userId);

    //根据类型编号和通知编号删除通知信息
    Integer delNotOneByNotIdAndUserId(@Param("functionId")String functionId
            ,@Param("typeId")Integer typeId);

    //根据通知编号删除个人信息
    Integer delNotOneByNotId(@Param("userId")String userId,@Param("functionId")String functionId
            ,@Param("typeId")Integer typeId);
}
