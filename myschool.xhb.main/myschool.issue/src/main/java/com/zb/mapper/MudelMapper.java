package com.zb.mapper;

import com.zb.entity.Mudel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MudelMapper {
    // 根据功能类型编号获取模板
    List<Mudel>getMudelByTypeId(@Param("typeId")Integer typeId);

    //根据模板编号获取模板信息
    Mudel getMudelById(@Param("mudelId")Integer mudelId);
}
