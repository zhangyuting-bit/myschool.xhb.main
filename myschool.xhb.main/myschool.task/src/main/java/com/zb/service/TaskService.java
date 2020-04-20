package com.zb.service;

import com.zb.pojo.Task;

import java.util.List;

public interface TaskService {

    int addTask(Task task);
    //展示全部待批改作业
    List<Task> listNoCorrectTasks();
    //展示全部需订正作业
    List<Task> listEmendTasks();
    //展示全部已通过作业
    List<Task> listPassdTasks();
    //展示全部作业
    List<Task> listAllTasks();
    //仅展示自己提交的作业
    List<Task> listPersonalTask();
    //修改作业状态
    int changeStatus(String taskId,Integer status);
    //订正作业
    int reviseTask(Task task);
    //撤销作业
    int deleteTask(String taskId);

}
