package com.zb.pojo;

import java.io.Serializable;

/**
 * 请假任务表
 */
public class Leave_job implements Serializable {
    private String id;
    //班级编号
    private Integer class_number;
    //哪位同学 id请假
    private Integer student_id;
    //请假的类型 1：事假 2：病假
    private Integer leave_type;
    //开始时间
    private String leave_time;
    //请假的结束时间
    private String end_time;
    //请假批准的状态
    private int state;
    //说明
    private String reason;
    private String leaveimg;
    private String updatedTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getClass_number() {
        return class_number;
    }

    public void setClass_number(Integer class_number) {
        this.class_number = class_number;
    }

    public Integer getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Integer student_id) {
        this.student_id = student_id;
    }

    public Integer getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(Integer leave_type) {
        this.leave_type = leave_type;
    }

    public String getLeave_time() {
        return leave_time;
    }

    public void setLeave_time(String leave_time) {
        this.leave_time = leave_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLeaveimg() {
        return leaveimg;
    }

    public void setLeaveimg(String leaveimg) {
        this.leaveimg = leaveimg;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }
}
