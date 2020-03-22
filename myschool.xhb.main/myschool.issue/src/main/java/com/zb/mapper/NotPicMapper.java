package com.zb.mapper;

import com.zb.entity.NotPic;
import org.apache.ibatis.annotations.Param;

public interface NotPicMapper {
    //添加图片
    Integer addNotPic(NotPic notPic);

    //根据通知编号查询图片数量
    Integer getPicCount(@Param("functionId") String functionId);
}
