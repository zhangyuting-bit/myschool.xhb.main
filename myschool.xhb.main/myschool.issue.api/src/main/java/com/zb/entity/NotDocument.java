package com.zb.entity;

import java.io.Serializable;

public class NotDocument implements Serializable {
    private String documentId;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    private String functionId;

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    private String documentSrc;
    public String getDocumentSrc() {
        return documentSrc;
    }

    public void setDocumentSrc(String documentSrc) {
        this.documentSrc = documentSrc;
    }
}
