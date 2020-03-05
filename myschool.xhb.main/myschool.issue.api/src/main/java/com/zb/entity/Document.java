package com.zb.entity;

public class Document {
    private Integer documentId;
    private Integer typeId;
    private Integer functionId;
    private String documentSrc;

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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
