package com.zb.pojo;

import java.io.Serializable;

/**学科类
 * @author
 */
public class Class_Subject implements Serializable {
    private Integer id;
    private  String subjectName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
