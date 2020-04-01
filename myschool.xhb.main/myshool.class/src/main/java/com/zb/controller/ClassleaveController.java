package com.zb.controller;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.pojo.Leave_job;
import com.zb.service.ClassleaveService;
import com.zb.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
public class ClassleaveController{
    @Autowired
    private ClassleaveService classleaveService;
    @Value("${qiniu.path}")
    private String path;
    private String key=null;
    @PostMapping("/addleave")
    public Dto classfiles(HttpServletRequest request, @RequestParam(required = false,value = "files") MultipartFile[] files, Leave_job leaveJob)throws Exception{
        for (MultipartFile file : files){
            if (Objects.isNull(file) || file.isEmpty()){
                return DtoUtil.returnSuccess("文件为空，请重新上传");
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
                Response response = classleaveService.uploadFile(destFile);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                key=putRet.key;//这个就是从七牛云获取的文件名
                leaveJob.setId(IdWorker.getId());
                leaveJob.setLeaveimg(path+key);
                classleaveService.addleave(leaveJob);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return DtoUtil.returnSuccess("ok",leaveJob);
    }
    @GetMapping("/leavejobshow/{class_number}")
    public Dto leavejobshow(@PathVariable("class_number")Integer class_number){
        return DtoUtil.returnSuccess("ok", classleaveService.findleavenumber(class_number));
    }
    //同意
    @PostMapping("/leaveagree/{id}")
    public Dto leaveagree(@PathVariable("id")String id){
        return DtoUtil.returnSuccess("ok", classleaveService.updateleaveAgree(id));
    }
    //拒绝
    @PostMapping("/leavefuse/{id}")
    public Dto leavefuse(@PathVariable("id")String id){
        return DtoUtil.returnSuccess("ok", classleaveService.updateleavefuse(id));
    }
    //查看记录请假的详情
    @GetMapping("/levaeshow")
    public Dto levaeshow(){
        return DtoUtil.returnSuccess("ok", classleaveService.fingleaveList());
    }
}
