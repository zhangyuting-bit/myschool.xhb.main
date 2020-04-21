package com.zb.vo;

import com.zb.entity.JobTask;

import java.io.Serializable;
import java.util.List;

//定时任务工具类
public class JobVo implements Serializable {
    //编号
    private Integer id;
    //定时任务集合
    private List<JobTask>jobTasks;

    public Integer getId(){
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<JobTask> getJobTasks() {
        return jobTasks;
    }

    public void setJobTasks(List<JobTask> jobTasks) {
        this.jobTasks = jobTasks;
    }
}
