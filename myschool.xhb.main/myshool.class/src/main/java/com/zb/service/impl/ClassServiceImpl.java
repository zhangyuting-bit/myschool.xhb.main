package com.zb.service.impl;

import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zb.config.RabbitConfig;
import com.zb.mapper.ClassInfoMapper;
import com.zb.mapper.ClassMapper;
import com.zb.pojo.*;
import com.zb.service.ClassService;
import com.zb.util.IdWorker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService, InitializingBean {
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private Auth auth;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RabbitConfig rabbitConfig;
    @Autowired
    private ClassInfoMapper classInfoMapper;
    @Value("${qiniu.bucket}")
    private String bucket;
    private StringMap putPolicy;
    String key=null;
    @Override
    public int addClass(Class_add classAdd) {
        int cont = classMapper.addClass(classAdd);
        if(cont>0){
            rabbitTemplate.convertAndSend(rabbitConfig.myexchange,"inform.class",classAdd);
        }
        return cont;
    }

    @Override
    public Response uploadFile(File file) throws Exception {
        Response response = this.uploadManager.put(file, key, getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, key, getUploadToken());
            retry++;
        }
        return response;
    }

    @Override
    public List<Class_real> getReal() {
        return classMapper.getReal();
    }

    @Override
    public List<Class_Subject> getSubject() {
        return classMapper.getSubject();
    }

    @Override
    public List<Class_age_real> getAgeReal(Integer real_id) {
        return classMapper.getAgeReal(real_id);
    }

    @Override
    public Class_add findClassBy(Integer class_number) {
        return classMapper.getClassBy(class_number);
    }

    @Override
    public int updateClass(Class_add classes) {
        return classMapper.updateClass(classes);
    }

    @Override
    public List<Class_add> findClassesList(Integer teacher_id) {
        List<Class_add> classesBy = classMapper.findClassesBy(teacher_id);
        for (Class_add classAdd : classesBy) {
            Integer classcount=classInfoMapper.classcount(classAdd.getClass_number());
            classAdd.setClass_count(classcount);
        }
        return classesBy;
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
