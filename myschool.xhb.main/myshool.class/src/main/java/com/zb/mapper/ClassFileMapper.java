package com.zb.mapper;

import com.zb.pojo.Class_File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ClassFileMapper {
    //存储文件的信息
    int addclassfile(Class_File classFile);
    List<Class_File> findclassfilelist();
    //根据id获取一个文件的对象信息
    Class_File findfileBy(@Param("spec")String spec);
}
