package com.zb.service;

import com.zb.entity.Document;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DocumentService {
    //根据功能编号获取对应文件
   public List<Document> getDocumentByNId(String functionId);
}
