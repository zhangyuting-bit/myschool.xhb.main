package com.zb.service.impl;

import com.zb.entity.SubjectAdd;
import com.zb.mapper.SubjectAddMapper;
import com.zb.service.SubjectAddService;
import com.zb.util.IdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SubjectAddServiceImpl implements SubjectAddService {
    @Resource
    private SubjectAddMapper subjectAddMapper;

    ///新增科目信息
    @Override
    public Integer addSubjectAdd(SubjectAdd subjectAdd) {
        subjectAdd.setId(IdWorker.getId());
        return subjectAddMapper.addSubjectAdd(subjectAdd);
    }

    //根据班级编号查询新增科目
    @Override
    public List<SubjectAdd> getByGradeId(String gradeId) {
        return subjectAddMapper.getByGradeId(gradeId);
    }

    //删除新增科目
    @Override
    public Integer delSubjectAdd(String id) {
        return subjectAddMapper.delSubjectAdd(id);
    }
}
