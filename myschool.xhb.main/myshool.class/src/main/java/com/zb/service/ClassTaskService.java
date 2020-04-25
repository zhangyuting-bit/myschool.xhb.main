package com.zb.service;

import com.zb.pojo.ClassTask;

import java.util.List;

public interface ClassTaskService {
    public List<ClassTask> findBeforOneMinuteTaskList();

    public void publish(String id , String exchange , String routingKey);

    public int updateTask(String id , Integer version);

    public Integer delTaskByCount();

    public Integer updateTaskCount(String id);
}
