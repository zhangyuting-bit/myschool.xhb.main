package com.zb.vo;

import com.zb.entity.Document;

import java.io.File;
import java.io.Serializable;

public class FileVo implements Serializable {
    private File file;
    private Document document;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
