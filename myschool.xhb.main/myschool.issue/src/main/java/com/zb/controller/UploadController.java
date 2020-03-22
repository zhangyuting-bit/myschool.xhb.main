package com.zb.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import com.zb.config.RabbitConfigs;
import com.zb.entity.Mudel;
import com.zb.entity.NotDocument;
import com.zb.entity.NotPic;
import com.zb.entity.Notification;
import com.zb.mapper.NotDocumentMapper;
import com.zb.mapper.NotPicMapper;
import com.zb.mapper.NotificationMapper;
import com.zb.service.NotificationService;
import com.zb.service.UploadService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {
    @Value("${qiniu.path}")
    public  String path;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private NotificationService notificationService;

    @Resource
    private NotDocumentMapper documentMapper;

    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private NotPicMapper notPicMapper;

    @Autowired
    private UploadService uploadService;
    @PostMapping("/singlefile/{functionId}/{gradeId}")
    public Object singleFileUpload(HttpServletRequest request, @RequestParam(required = false,value = "files") MultipartFile[] files,@PathVariable("functionId") String functionId,@PathVariable("gradeId") String gradeId) {
        for (MultipartFile file : files) {
            if (Objects.isNull(file) || file.isEmpty()) {
                return "文件为空，请重新上传";
            }
            try{
                //根据时间戳创建文件名
                String fileName = System.currentTimeMillis() + file.getOriginalFilename();
                //创建文件的实际路径
                String destFileName = request.getServletContext().getRealPath("") + "uploaded" + File.separator + fileName;
                //根据文件路径创建文件对应的实际文件
                File destFile = new File(destFileName);
                //创建文件实际路径
                destFile.getParentFile().mkdirs();
                //将文件传到对应的文件位置
                file.transferTo(destFile);
                Response response = uploadService.uploadFile(destFile);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                String str=file.getContentType().split("/")[1];
                System.out.println(str);
                if (str.equals("bmp")||str.equals("jpg")||str.equals("gif")||str.equals("png")){
                    NotPic notPic=new NotPic();
                    notPic.setPicId(IdWorker.getId());
                    notPic.setFunctionId(functionId);
                    notPic.setPicSrc(path+""+putRet.key);
                    if (notPicMapper.getPicCount(functionId)>0){
                        notPic.setStatu(1);
                    }else {
                        notPic.setStatu(0);
                        String key="notification:"+gradeId;
                        Object o = redisUtil.get(key);
                        Notification notification=JSON.parseObject(o.toString(),Notification.class);
                        notification.setPicSrc(notPic.getPicSrc());
                        redisUtil.set(key, JSON.toJSONString(notification));
                    }
                    notPicMapper.addNotPic(notPic);
                }else if (str.equals("wav")||str.equals("mp3")||str.equals("wma")||str.equals("mp4")){
                    String key="notification:"+gradeId;
                    Object o = redisUtil.get(key);
                    Notification notification=JSON.parseObject(o.toString(),Notification.class);
                    notification.setAudioSrc(path+""+putRet.key);
                    notificationMapper.updateVdoAndAudio(notification);
                }else if (str.equals("avi")||str.equals("mov")||str.equals("octet-stream")){
                    String key="notification:"+gradeId;
                    Object o = redisUtil.get(key);
                    Notification notification=JSON.parseObject(o.toString(),Notification.class);
                    notification.setVideoSrc(path+""+putRet.key);
                    notificationMapper.updateVdoAndAudio(notification);
                }else {
                    NotDocument document=new NotDocument();
                    document.setDocumentId(IdWorker.getId());
                    document.setFunctionId(functionId);
                    document.setDocumentSrc(path+""+putRet.key);
                    documentMapper.addDocument(document);
                }
            }catch (IOException e){
                e.printStackTrace();
                //System.out.println(putRet.key);//这个就是从七牛云获取的文件名
            }
//            announceService.save(announce);  //存入数据库

            /*try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get("F:\\img\\" + file.getOriginalFilename());
                //如果没有files文件夹，则创建
                if (!Files.isWritable(path)) {
                    Files.createDirectories(Paths.get("F:\\img\\"));
                }
                //文件写入指定路径
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
                return "后端异常...";
            }*/
        }
        return "文件上传成功";
    }
}