package com.zb.service.impl;

import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zb.config.RabbitConfig;
import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.mapper.ClassInfoMapper;
import com.zb.mapper.ClassleaveMapper;
import com.zb.pojo.Class_leave;
import com.zb.pojo.Leave_job;
import com.zb.service.ClassleaveService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service

public class ClassleaveServiceImpl implements ClassleaveService, InitializingBean {
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Auth auth;
    @Autowired
    private ClassleaveMapper classleaveMapper;
    @Value("${qiniu.bucket}")
    private String bucket;
    private StringMap putPolicy;
    String key=null;
    @Override
    public int addleave(Leave_job leaveJob) {
        return classleaveMapper.addleavejob(leaveJob);
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
    public Dto updateleaveAgree(String id) {
        //同意请假
        int agree = classleaveMapper.updataleaveAgree(id);
        if(agree==1){
            Leave_job job = classleaveMapper.getleaveBy(id);
            rabbitTemplate.convertAndSend(RabbitConfig.myexchange, "inform.job",job);
            //并删除这条任务
            classleaveMapper.deleteleave(id);
        }
        return DtoUtil.returnSuccess("agree");
    }

    @Override
    public Dto updateleavefuse(String id) {
        //同意请假
        int agree = classleaveMapper.updataleaverefuse(id);
        if(agree==1){
            Leave_job job = classleaveMapper.getleaveBy(id);
            rabbitTemplate.convertAndSend(RabbitConfig.myexchange, "inform.job",job);
            //并删除这条任务
            classleaveMapper.deleteleave(id);
        }
        return DtoUtil.returnSuccess("fuse");
    }

    @Override
    public List<Leave_job> findleavenumber(Integer class_number) {
        return classleaveMapper.findleavenumber(class_number);
    }

    @Override
    public List<Leave_job> findleaveBy() {
        return classleaveMapper.findleaveBy();
    }

    @Override
    public int addclassleave(Class_leave classLeave) {
        return classleaveMapper.addclassleave(classLeave);
    }

    @Override
    public List<Class_leave> fingleaveList() {
        return classleaveMapper.fingleaveList();
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
