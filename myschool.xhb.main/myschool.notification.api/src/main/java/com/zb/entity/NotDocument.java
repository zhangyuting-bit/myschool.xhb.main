package com.zb.entity;

import java.io.Serializable;

//通知,习惯,讨论,作业,活动附件类
public class NotDocument implements Serializable {
    //附件编号
    private String documentId;
    //通知编号
    private String functionId;
    //附件路径
    private String documentSrc;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getDocumentSrc() {
        return documentSrc;
    }

    public void setDocumentSrc(String documentSrc) {
        this.documentSrc = documentSrc;
    }
}
