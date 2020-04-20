package com.zb.mapper;

import com.zb.pojo.Lable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LableMapper {

    //展示系统公共标签
    List<Lable> listAllPublicLables();
    //展示个人自增标签
    List<Lable> listPersonalLables(@Param("userId") String userId);
    //新增个人标签
    int addLable(Lable lable);
    //删除标签
    int delectLable(@Param("lableId") Integer lableId);

}
