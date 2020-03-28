package com.zb.service;

import com.qiniu.http.Response;
import com.zb.dto.Dto;
import com.zb.pojo.Class_File;

import java.io.File;

public interface ClassFileService {
    //存储文件的信息
    int addclassfile(Class_File classFile);
    //上传到七牛云
    Response uploadFile(File file) throws Exception;
    //从七牛云下载文件
    Dto download(String targetUrl);
}
