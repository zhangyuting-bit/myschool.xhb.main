package com.zb.service.impl;

import com.zb.entity.Document;
import com.zb.mapper.DocumentMapper;
import com.zb.service.DocumentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Resource
    private DocumentMapper documentMapper;

    @Override
    public List<Document> getDocumentByNId(String functionId) {
        return documentMapper.getDocumentByNId(functionId);
    }
}
