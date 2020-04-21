package com.zb.vo;

import com.zb.pojo.Class_add;

import java.io.Serializable;
import java.util.List;

public class UserVo implements Serializable {
    //班级集合
    private List<String>gradeList;

    public List<String> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<String> gradeList) {
        this.gradeList = gradeList;
    }
}
