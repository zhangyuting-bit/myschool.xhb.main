package com.zb.controller;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.pojo.Class_File;
import com.zb.service.ClassFileService;
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
public class ClassFileController {
    @Autowired
    private ClassFileService classFileService;
    @Value("${qiniu.path}")
    private String path;
    private String key=null;
    @PostMapping("/addfile")
    public Dto addfile(HttpServletRequest request, @RequestParam(required = false,value = "files") MultipartFile[] files, Class_File classFile)throws Exception{
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
                Response response = classFileService.uploadFile(destFile);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                String filename = file.getOriginalFilename();
                String fileNameExtension = filename.substring(filename.lastIndexOf(".")+1);
                String realName = String.valueOf(System.currentTimeMillis())+ fileNameExtension;
                key=putRet.key;//这个就是从七牛云获取的文件名
//                System.out.println(filename+"~~~~~~~~~~~~");
//                System.out.println(fileNameExtension+"!!!!!!!!!!!!!!!");
                classFile.setId(IdWorker.getId());
                classFile.setImage(path);
                classFile.setUser_id(1);
                classFile.setSpec(key);
                classFile.setMemory(fileNameExtension);
                classFileService.addclassfile(classFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return DtoUtil.returnSuccess("ok" );
    }
    @GetMapping("/filedownload")
    public Dto filedownload(@RequestParam(value = "targetUrl",required = false) String targetUrl){
        classFileService.download(targetUrl);
        return DtoUtil.returnSuccess("下载成功");
    }
}
