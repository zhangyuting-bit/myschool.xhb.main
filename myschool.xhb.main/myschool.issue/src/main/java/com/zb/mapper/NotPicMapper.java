package com.zb.mapper;

import com.zb.entity.NotPic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotPicMapper {
    //添加图片
    Integer addNotPic(NotPic notPic);

    //根据通知编号查询图片数量
    Integer getPicCount(@Param("functionId") String functionId);

    //根据通知编号查询图片
    List<NotPic>getPicByFId(@Param("functionId") String functionId);

    //根据通知编号查询状态为0的图片
    NotPic getPicByStatu(@Param("functionId") String functionId);
}
