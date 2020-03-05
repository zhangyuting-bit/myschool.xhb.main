package com.zb.entity;

import java.io.Serializable;

public class Document implements Serializable {
    private Integer documentId;
    private Integer functionId;
    private String documentSrc;

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public String getDocumentSrc() {
        return documentSrc;
    }

    public void setDocumentSrc(String documentSrc) {
        this.documentSrc = documentSrc;
    }
}
