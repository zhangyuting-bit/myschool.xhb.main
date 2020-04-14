package com.zb.service;

import com.zb.entity.SubjectAdd;

import java.util.List;

public interface SubjectAddService {
    ///新增科目信息
    Integer addSubjectAdd(SubjectAdd subjectAdd);

    //根据班级编号查询新增科目
    List<SubjectAdd> getByGradeId(String gradeId);

    //删除新增科目
    Integer delSubjectAdd(String id);

}
