package com.zb.controller;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.pojo.Class_File;
import com.zb.pojo.Class_Timetable;
import com.zb.pojo.Class_add;
import com.zb.service.ClassService;
import com.zb.service.ClassTimetableService;
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
public class ClassTimetableController {
    @Autowired
    private ClassTimetableService classTimetableService;
    @Autowired
    private ClassService classService;
    @Value("${qiniu.path}")
    private String path;
    private String key=null;
    @PostMapping("/addtimetable")
    public Dto addfile(HttpServletRequest request, @RequestParam(required = false,value = "files") MultipartFile[] files, Class_Timetable timetable)throws Exception{
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
                Response response = classTimetableService.uploadFile(destFile);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                key=putRet.key;//这个就是从七牛云获取的文件名
                System.out.println(timetable.getClass_number()+"!!!班级号");
                Class_add classBy = classService.findClassBy(timetable.getClass_number());
                timetable.setId(IdWorker.getId());
                timetable.setClass_number(timetable.getClass_number());
                timetable.setClass_emblem(classBy.getClass_emblem());
                timetable.setTeacherName(classBy.getTeacherName());
                timetable.setClass_name(classBy.getClass_Name());
                timetable.setTimetableimage(path+key);
                classTimetableService.addtimetable(timetable);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return DtoUtil.returnSuccess("ok" );
    }
    @GetMapping("/timetableshow/{class_number}")
    public Dto timetableshow(@PathVariable("class_number")Integer class_number){
        return DtoUtil.returnSuccess("ok", classTimetableService.findgettimetableBy(class_number));
    }
    @PostMapping("/updatetimetable")
    public Dto updatetimetable(HttpServletRequest request, @RequestParam(required = false,value = "files") MultipartFile[] files, Class_Timetable timetable)throws Exception{
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
                Response response = classTimetableService.uploadFile(destFile);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                key=putRet.key;//这个就是从七牛云获取的文件名

                timetable.setTimetableimage(path+key);
                classTimetableService.updatatimetable(timetable);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return DtoUtil.returnSuccess("ok" );
    }
    @PostMapping("/deletetimetable/{class_number}")
    public Dto deletetimetable(@PathVariable("class_number")Integer class_number){
        return DtoUtil.returnSuccess("ok", classTimetableService.deletetimetable(class_number));
    }
}
