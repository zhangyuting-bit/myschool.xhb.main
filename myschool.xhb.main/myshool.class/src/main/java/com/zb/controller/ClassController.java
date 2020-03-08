package com.zb.controller;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.pojo.Class_Subject;
import com.zb.pojo.Class_add;
import com.zb.service.ClassService;
import com.zb.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

@RestController
@CrossOrigin
public class ClassController {
    @Autowired
    private ClassService classService;
    @Value("${qiniu.path}")
    private String path;
    private String key=null;
    @PostMapping("/classfiles")
    public String classfiles(HttpServletRequest request, @RequestParam(required = false,value = "files") MultipartFile[] files,Class_add classAdd)throws Exception{
        for (MultipartFile file : files) {
            if (Objects.isNull(file) || file.isEmpty()) {
                return "文件为空，请重新上传";
            }
            try {
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
                Response response = classService.uploadFile(destFile);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                key=putRet.key;//这个就是从七牛云获取的文件名
                Random random=new Random();
                Integer number= (int)(random.nextDouble()*(99999-10000 + 1))+ 10000;
                classAdd.setClass_number(number);
                classAdd.setId(IdWorker.getId());
                classAdd.setTeacher_id(1);
                classAdd.setClass_emblem(path+key);
                classService.addClass(classAdd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "创建成功！";
    }
    @GetMapping("/subject")
    public Dto subject(){
        return DtoUtil.returnSuccess("ok", classService.getSubject());
    }
    @GetMapping("/real")
    public Dto real(){
        return DtoUtil.returnSuccess("ok", classService.getReal());
    }
    @GetMapping("/agereal/{real_id}")
    public Dto agereal(@PathVariable("real_id")Integer real_id){
        return DtoUtil.returnSuccess("ok", classService.getAgeReal(real_id));
    }
}
