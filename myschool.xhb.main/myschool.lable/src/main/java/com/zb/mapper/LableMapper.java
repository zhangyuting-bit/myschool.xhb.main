package com.zb.mapper;

import com.zb.pojo.Lable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LableMapper {

    List<Lable> listAllPublicLables();
    List<Lable> listPersonalLables(@Param("userId") String userId);
    int addLable(Lable lable);
    int delectLable(@Param("lableId") Integer lableId);

}
