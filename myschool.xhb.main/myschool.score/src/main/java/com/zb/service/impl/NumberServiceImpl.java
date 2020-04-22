package com.zb.service.impl;

import com.zb.config.RabbitConfig;
import com.zb.entity.StuNumber;
import com.zb.mapper.NumberMapper;
import com.zb.pojo.Class_Jobinfo;
import com.zb.service.NumberService;
import com.zb.util.IdWorker;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NumberServiceImpl implements NumberService {
    @Resource
    private NumberMapper numberMapper;

    ///添加学生学号
    @Override
    public Integer addNumber(StuNumber number) {
        number.setNumberId(IdWorker.getId());
        return numberMapper.addNumber(number);
    }

    //学生加入班级时添加学生学号
    @RabbitListener(queues = RabbitConfig.classinfo)
    public void intoClass(Class_Jobinfo jobinfo){
        StuNumber stuNumber=new StuNumber();
        stuNumber.setNumberId(IdWorker.getId());
        stuNumber.setGradeId(jobinfo.getClass_number().toString());
        stuNumber.setStuId(jobinfo.getNumber().toString());
        stuNumber.setStuName(jobinfo.getCall_name());
        numberMapper.addNumber(stuNumber);
    }

    //学生退出班级时删除学生学号
    //@RabbitListener(queues = RabbitConfig.delQueue)
    public void delClass(String gradeId,String userId){
        numberMapper.delClass(gradeId,userId);
    }

    //根据班级编号查询学生学号
    @Override
    public List<StuNumber> getNumberByGradeId(String gradeId) {
        return numberMapper.getNumberByGradeId(gradeId);
    }

    //根据班级编号修改学生学号信息
    @Override
    public Integer updateNumber(String numberId) {
        return numberMapper.updateNumber(numberId);
    }
}
