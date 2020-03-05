package com.zb.mapper;

import com.zb.entity.Mudel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MudelMapper {
    List<Mudel>getMudelByTypeId(@Param("typeId")Integer typeId);
}
