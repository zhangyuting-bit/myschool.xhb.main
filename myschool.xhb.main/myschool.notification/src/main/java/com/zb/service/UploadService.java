package com.zb.service;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;

import java.io.File;

/**
 */
public interface UploadService {
    /**
     * 上传文件
     * @param file File
     * @return
     * @throws QiniuException
     */
    Response uploadFile(File file) throws QiniuException;
}