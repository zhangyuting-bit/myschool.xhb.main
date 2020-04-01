package com.zb.service.impl;

import com.zb.entity.StuSubject;
import com.zb.mapper.StuSubjectMapper;
import com.zb.service.StuSubjectService;
import com.zb.util.IdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StuSubjectServiceImpl implements StuSubjectService {
    @Resource
    private StuSubjectMapper stuSubjectMapper;

    //添加科目成绩
    @Override
    public Integer addStuSubject(StuSubject stuSubject) {
        stuSubject.setSjId(IdWorker.getId());
        return stuSubjectMapper.addStuSubject(stuSubject);
    }
}
