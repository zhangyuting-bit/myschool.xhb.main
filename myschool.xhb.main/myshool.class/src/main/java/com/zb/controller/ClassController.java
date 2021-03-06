package com.zb.controller;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.feign.UserFeignClient;
import com.zb.pojo.Class_Subject;
import com.zb.pojo.Class_add;
import com.zb.pojo.Class_info;
import com.zb.pojo.UserInfo;
import com.zb.service.ClassInfoService;
import com.zb.service.ClassService;
import com.zb.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RestController
@CrossOrigin
public class ClassController {
    @Autowired
    private ClassService classService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private UserFeignClient userFeignClient;
    @Value("${qiniu.path}")
    private String path;
    private String key=null;
    @PostMapping("/classfiles/{token}")
    public Dto classfiles(HttpServletRequest request, @RequestParam(required = false,value = "files") MultipartFile[] files,Class_add classAdd,@PathVariable(value = "token")String token)throws Exception{
        UserInfo userBy = userFeignClient.getUserInfoByToken(token);
        for (MultipartFile file : files) {
            if (Objects.isNull(file) || file.isEmpty()) {
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
                Response response = classService.uploadFile(destFile);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                key=putRet.key;//这个就是从七牛云获取的文件名
                Random random=new Random();
                Integer number= (int)(random.nextDouble()*(99999-10000 + 1))+ 10000;
                classAdd.setClass_number(number);
                classAdd.setId(IdWorker.getId());
                classAdd.setTeacher_id(userBy.getId());
                classAdd.setTeacherName(userBy.getName());//这里根据用户的id获取用户的名称
                classAdd.setClass_emblem(path+key);
                classService.addClass(classAdd);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return DtoUtil.returnSuccess("ok", classAdd);
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
    //修改班级的信息
    @PostMapping("/updateclass")
    public Dto updateclass(Class_add classes){
        return DtoUtil.returnSuccess("ok", classService.updateClass(classes));
    }

    //根据班号查看班级的信息
    @GetMapping("/classnumber/{class_number}")
    public Dto classnumber(@PathVariable("class_number")Integer class_number){
        return DtoUtil.returnSuccess("ok", classService.findClassBy(class_number));
    }
    //查询老师所 创建的班级集合
    @GetMapping("/showclass/{teacher_id}")
    public Dto showclass(@PathVariable("teacher_id")Integer teacher_id){
        return DtoUtil.returnSuccess("ok", classService.findClassesList(teacher_id));
    }
    //获取班级内部人员的信息
    @GetMapping("/classinfo/{class_number}")
    public List<Class_info> classinfo(@PathVariable("class_number")Integer class_number){
        return classInfoService.findClassinfoBy(class_number);
    }
    @PostMapping("/updateclassinfo")
    public Dto updateclassinfo(Class_info classinfo){
        return DtoUtil.returnSuccess("ok", classInfoService.updateClassInfo(classinfo));
    }
    //获取某个成员的信息
    @GetMapping("/classinfoby/{id}")
    public Dto classinfoby(@PathVariable("id")String id){
        return DtoUtil.returnSuccess("ok", classInfoService.getClassInfoBy(id));
    }
    @PostMapping("/addclassjob/{class_number}/{token}")
    public Dto addclassjob(
            Class_info classInfo,@PathVariable("class_number")Integer class_number,@PathVariable("token")String  token){
        return DtoUtil.returnSuccess("ok", classInfoService.addnumberClassInfo(classInfo,class_number,token));
    }
    //
    @GetMapping("/addclassinfo/{class_number}")
    public Dto addclassinfo(@PathVariable("class_number")Integer class_number){
        return DtoUtil.returnSuccess("ok",classInfoService.classinfojob(class_number));
    }
    //获取某个成员的信息
    @GetMapping("/dletejob/{id}")
    public Dto dletejob(@PathVariable("id")String  id){
        return DtoUtil.returnSuccess("ok", classInfoService.classdeletejobs(id));
    }
    //根据班号监控查询有没有人申请加入此班级 等待老师通过
    @GetMapping("/classjobshow/{class_number}")
    public Dto classjobshow(@PathVariable("class_number")Integer  class_number){
        return DtoUtil.returnSuccess("ok",classInfoService.findclassjob(class_number));
    }
    @GetMapping("/jobshowby/{class_number}")
    public Dto jobshowby(@PathVariable("class_number")Integer  class_number){
        return DtoUtil.returnSuccess("ok",classInfoService.findClassjobBy(class_number));
    }
    //静态化页面分享
    @GetMapping("/classinforby/{class_number}")
    public Class_add classinforby(@PathVariable("class_number")Integer class_number){
        System.out.println("查询数据库");
        return classService.findclassinforBy(class_number);
    }
    //根据id获取班级的信息
    @GetMapping("/showclassid/{class_number}")
    public Class_add showclass(@PathVariable(value = "class_number")String class_number){
        return classService.findClassByid(class_number);
    }
    //根据用户的id获取该用户所在的所有班级
    @GetMapping("/userclassinfo/{user_id}")
    public List<Class_info> showclassid(@PathVariable(value = "user_id")Integer user_id){
        return classInfoService.findinfouserid(user_id);
    }
}
