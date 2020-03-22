package com.zb.service;

import com.zb.entity.NotDocument;

import java.util.List;

public interface NotDocumentService {
    //根据功能编号获取对应文件
   public List<NotDocument> getDocumentByNId(String functionId);
}
