package com.zb.mapper;

import com.zb.entity.Document;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocumentMapper {
    //添加文件
    Integer addDocument(Document document);

    //根据功能编号获取对应文件
    List<Document>getDocumentByNId(@Param("functionId")String functionId);
}
