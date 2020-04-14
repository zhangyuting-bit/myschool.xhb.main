package com.zb.service.impl;

import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.zb.mapper.ClassMapper;
import com.zb.mapper.ClassTimetableMapper;
import com.zb.pojo.Class_Timetable;
import com.zb.pojo.Class_add;
import com.zb.service.ClassTimetableService;
import com.zb.util.IdWorker;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
@Service
public class ClassTimetableServiceImpl implements ClassTimetableService, InitializingBean {
    @Autowired
    private ClassTimetableMapper classTimetableMapper;
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private Auth auth;
    @Value("${qiniu.bucket}")
    private String bucket;
    private StringMap putPolicy;
    String key=null;
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
    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(bucket, null, 3600,putPolicy);
    }

    @Override
    public int addtimetable(Class_Timetable timetable) {
//        Class_add classBy = classMapper.getClassBy(timetable.getClass_number());
//        if(classBy!=null){
//            Class_Timetable classTimetable=new Class_Timetable();
//            classTimetable.setId(IdWorker.getId());
//            classTimetable.setClass_number(classBy.getClass_number());
//            classTimetable.setClass_emblem(classBy.getClass_emblem());
//            classTimetable.setClass_name(classBy.getClass_Name());
//            classTimetable.setTeacherName(classBy.getTeacherName());
//            classTimetable.setTimetableimage(timetable.getTimetableimage());
//            int addtimetable = classTimetableMapper.addtimetable(classTimetable);
//            return addtimetable;
//        }
        return classTimetableMapper.addtimetable(timetable);
    }

    @Override
    public Class_Timetable findgettimetableBy(Integer class_number) {
        return classTimetableMapper.findgettimetableBy(class_number);
    }

    @Override
    public int updatatimetable(Class_Timetable timetable) {
        return classTimetableMapper.updatatimetable(timetable);
    }

    @Override
    public int deletetimetable(Integer class_number) {
        return classTimetableMapper.deletetimetable(class_number);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
    }
}
