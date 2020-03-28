package com.zb.pojo;

import java.io.Serializable;

public class TeacherJob implements Serializable {
    private String jobId;
    private Integer jobday;
    private Integer amhourstart;
    private Integer amminstart;
    private Integer amhourend;
    private Integer amminend;
    private Integer pmhourstart;
    private Integer pmminstart;
    private Integer pmhourend;
    private Integer pmminend;
    private String teacherId;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public Integer getJobday() {
        return jobday;
    }

    public void setJobday(Integer jobday) {
        this.jobday = jobday;
    }

    public Integer getAmhourstart() {
        return amhourstart;
    }

    public void setAmhourstart(Integer amhourstart) {
        this.amhourstart = amhourstart;
    }

    public Integer getAmminstart() {
        return amminstart;
    }

    public void setAmminstart(Integer amminstart) {
        this.amminstart = amminstart;
    }

    public Integer getAmhourend() {
        return amhourend;
    }

    public void setAmhourend(Integer amhourend) {
        this.amhourend = amhourend;
    }

    public Integer getAmminend() {
        return amminend;
    }

    public void setAmminend(Integer amminend) {
        this.amminend = amminend;
    }

    public Integer getPmhourstart() {
        return pmhourstart;
    }

    public void setPmhourstart(Integer pmhourstart) {
        this.pmhourstart = pmhourstart;
    }

    public Integer getPmminstart() {
        return pmminstart;
    }

    public void setPmminstart(Integer pmminstart) {
        this.pmminstart = pmminstart;
    }

    public Integer getPmhourend() {
        return pmhourend;
    }

    public void setPmhourend(Integer pmhourend) {
        this.pmhourend = pmhourend;
    }

    public Integer getPmminend() {
        return pmminend;
    }

    public void setPmminend(Integer pmminend) {
        this.pmminend = pmminend;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public TeacherJob() {
    }

    public TeacherJob(String jobId, Integer jobday, Integer amhourstart, Integer amminstart, Integer amhourend, Integer amminend, Integer pmhourstart, Integer pmminstart, Integer pmhourend, Integer pmminend, String teacherId) {
        this.jobId = jobId;
        this.jobday = jobday;
        this.amhourstart = amhourstart;
        this.amminstart = amminstart;
        this.amhourend = amhourend;
        this.amminend = amminend;
        this.pmhourstart = pmhourstart;
        this.pmminstart = pmminstart;
        this.pmhourend = pmhourend;
        this.pmminend = pmminend;
        this.teacherId = teacherId;
    }
}
