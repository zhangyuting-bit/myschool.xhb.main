package com.zb.mapper;

import com.zb.entity.SubjectAdd;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubjectAddMapper {
    //新增科目信息
    Integer addSubjectAdd(SubjectAdd subjectAdd);

    //根据班级编号查询新增科目
    List<SubjectAdd>getByGradeId(@Param("gradeId")String gradeId);

    //删除新增科目
    Integer delSubjectAdd(@Param("id")String id);
}
