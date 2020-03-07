package com.zb.service.impl;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zb.service.UploadService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author www.xyjz123.xyz
 * @date 2019/3/28 15:46
 */
@Service
public class UploadServiceImpl implements UploadService, InitializingBean {
    @Resource
    private UploadManager uploadManager;
    @Resource
    private Auth auth;

    @Value("${qiniu.path}")
    public  String path;

    @Value("${qiniu.bucket}")
    private String bucket;

    private StringMap putPolicy;
    String key = null;

    @Override
    public Response uploadFile(File file) throws QiniuException {
        Response response = this.uploadManager.put(file, key, getUploadToken());
        System.out.println(path);
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, key, getUploadToken());
            retry++;
        }
        return response;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(bucket, null, 3600, putPolicy);
    }
}