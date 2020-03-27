package com.zb.controller;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.service.UploadService;
import com.zb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private UserService userService;
    @PostMapping("/singlefile")
    public Dto singleFileUpload(HttpServletRequest request, @RequestParam(required = false,value = "files") MultipartFile[] files, @RequestParam(value = "id") String id) {
        Dto dto=null;
        for (MultipartFile file : files) {
            if (Objects.isNull(file) || file.isEmpty()) {
                return DtoUtil.returnFail("文件为空，请重新上传","002") ;
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
                System.out.println(putRet.key);  //这个就是从七牛云获取的文件名
                String userpic="http://q6phyfl2d.bkt.clouddn.com/"+putRet.key;
                dto = userService.updateUserPic(id, userpic);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return dto;
    }
}