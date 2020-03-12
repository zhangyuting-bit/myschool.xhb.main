package com.zb.controller;

import com.zb.entity.Document;
import com.zb.service.DocumentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DocumentController {
    @Resource
    private DocumentService documentService;

    @GetMapping("/getDocumentByNId/{functionId}")
    public List<Document> getDocumentByNId(@PathVariable("functionId") String functionId){
        return  documentService.getDocumentByNId(functionId);
    }
}
