package com.zb.service.impl;

import com.zb.entity.NotDocument;
import com.zb.mapper.NotDocumentMapper;
import com.zb.service.NotDocumentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NotDocumentServiceImpl implements NotDocumentService {
    @Resource
    private NotDocumentMapper documentMapper;

    @Override
    public List<NotDocument> getDocumentByNId(String functionId) {
        return documentMapper.getDocumentByNId(functionId);
    }
}
