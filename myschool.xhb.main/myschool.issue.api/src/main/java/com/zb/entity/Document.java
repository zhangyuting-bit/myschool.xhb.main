package com.zb.entity;

import java.io.Serializable;

public class Document implements Serializable {
    private Integer documentId;
    private String functionId;

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    private String documentSrc;

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public String getDocumentSrc() {
        return documentSrc;
    }

    public void setDocumentSrc(String documentSrc) {
        this.documentSrc = documentSrc;
    }
}
