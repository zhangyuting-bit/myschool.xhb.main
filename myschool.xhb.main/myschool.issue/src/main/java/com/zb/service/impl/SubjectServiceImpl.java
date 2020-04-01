package com.zb.service.impl;

import com.zb.entity.Subject;
import com.zb.mapper.SubjectMapper;
import com.zb.service.SubjectService;
import com.zb.util.IdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SubjectServiceImpl implements SubjectService {
    @Resource
    private SubjectMapper subjectMapper;

    //添加考试科目
    @Override
    public Integer addSubject(Subject subject) {
        subject.setSubjectId(IdWorker.getId());
        return subjectMapper.addSubject(subject);
    }
}
