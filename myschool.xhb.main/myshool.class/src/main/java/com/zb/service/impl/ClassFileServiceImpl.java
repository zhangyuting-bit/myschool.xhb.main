package com.zb.service.impl;

import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.mapper.ClassFileMapper;
import com.zb.pojo.Class_File;
import com.zb.service.ClassFileService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ClassFileServiceImpl implements ClassFileService, InitializingBean {
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private ClassFileMapper classFileMapper;
    @Autowired
    private Auth auth;
    @Value("${qiniu.bucket}")
    private String bucket;
    private StringMap putPolicy;
    String key=null;
    @Override
    public int addclassfile(Class_File classFile) {
        return classFileMapper.addclassfile(classFile);
    }

    @Override
    public Response uploadFile(File file) throws Exception {
        Response response = this.uploadManager.put(file, key, getUploadToken(file.getName()));
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, key, getUploadToken(file.getName()));
            retry++;
        }
        return response;
    }

    @Override
    public Dto download(String targetUrl) {
        //获取downloadUrl
        String downloadUrl = getDownloadUrl(targetUrl);
        //本地保存路径
        String filePath = "D:/image/";
        download(downloadUrl, filePath);
        return DtoUtil.returnSuccess("ok");
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
    private String getUploadToken(String filename) {
        return this.auth.uploadToken(bucket, null, 3600, new StringMap().putNotEmpty("saveKey",filename),true);
    }
    /**
     * 获取下载文件路径，即：donwloadUrl
     * @return
     */
    public String getDownloadUrl(String targetUrl) {
        String downloadUrl = auth.privateDownloadUrl(targetUrl);
        return downloadUrl;
    }
    /**
     * 通过发送http get 请求获取文件资源
     * @param url
     * @param filepath
     * @return
     */
    private static void download(String url, String filepath) {
        OkHttpClient client = new OkHttpClient();
        System.out.println(url);
        String strs=url.substring(url.indexOf("m")+2, url.indexOf("?"));
        System.out.println(strs);
        Request req = new Request.Builder().url(url).build();
        okhttp3.Response resp = null;
        try {
            resp = client.newCall(req).execute();
            System.out.println(resp.isSuccessful());
            if(resp.isSuccessful()) {
                ResponseBody body = resp.body();
                InputStream is = body.byteStream();
                byte[] data =readInputStream(is);
                File imgFile = new File(filepath +strs);          //下载到本地的图片命名
                FileOutputStream fops = new FileOutputStream(imgFile);
                fops.write(data);
                fops.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Unexpected code " + resp);
        }
    }

    /**
     * 读取字节输入流内容
     * @param is
     * @return
     */
    private static byte[] readInputStream(InputStream is) {
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        byte[] buff = new byte[1024 * 2];
        int len = 0;
        try {
            while((len = is.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return writer.toByteArray();
    }
}
