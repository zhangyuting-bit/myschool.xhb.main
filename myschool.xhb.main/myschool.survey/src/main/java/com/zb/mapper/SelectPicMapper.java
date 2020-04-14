package com.zb.mapper;

import com.zb.entity.SelectPic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SelectPicMapper {
    //根据题目获取题目图片
    List<SelectPic>getPicBySelectId(@Param("selectId")String selectId);

    //添加题目图片
    Integer addSelectPic(SelectPic selectPic);

    //根据题目编号删除图片信息
    Integer delPicBySelectId(@Param("selectId")String selectId);
}
