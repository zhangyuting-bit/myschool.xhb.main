package com.zb.mapper;

import com.zb.entity.NotDocument;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NotDocumentMapper {
    //添加文件
    Integer addDocument(NotDocument document);

    //根据功能编号获取对应文件
    List<NotDocument>getDocumentByNId(@Param("functionId")String functionId);

    //根据通知编号删除文件
    Integer delDocByNotId(@Param("functionId")String functionId);
}
