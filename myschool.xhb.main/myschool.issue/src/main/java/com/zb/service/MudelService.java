package com.zb.service;

import com.zb.entity.Mudel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MudelService {
    //根据功能类型编号获取模板
    public List<Mudel> getMudelByTypeId(Integer typeId);

     // 根据模板编号获取模板信息
    Mudel getMudelById(@Param("mudelId")Integer mudelId);
}
