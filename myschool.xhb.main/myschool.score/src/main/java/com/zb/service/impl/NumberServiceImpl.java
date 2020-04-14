package com.zb.service.impl;

import com.zb.entity.StuNumber;
import com.zb.mapper.NumberMapper;
import com.zb.service.NumberService;
import com.zb.util.IdWorker;
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

    //根据班级编号查询学生学号
    @Override
    public List<StuNumber> getNumberByGradeId(String gradeId) {
        return numberMapper.getNumberByGradeId(gradeId);
    }

    //根据班级编号删除学生学号信息
    @Override
    public Integer updateNumber(String numberId) {
        return numberMapper.updateNumber(numberId);
    }
}
