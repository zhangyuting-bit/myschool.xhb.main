package com.zb.service.impl;

import com.zb.mapper.TaskMapper;
import com.zb.pojo.Task;
import com.zb.service.TaskService;
import com.zb.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskMapper taskMapper;

    @Override
    public int addTask(Task task) {
        task.setTaskId(IdWorker.getId());
        return taskMapper.addTask(task);
    }

    @Override
    public List<Task> listNoCorrectTasks() {
        return taskMapper.listNoCorrectTasks();
    }

    @Override
    public List<Task> listEmendTasks() {
        return taskMapper.listEmendTasks();
    }

    @Override
    public List<Task> listPassdTasks() {
        return taskMapper.listPassdTasks();
    }

    @Override
    public List<Task> listAllTasks() {
        return taskMapper.listAllTasks();
    }

    @Override
    public List<Task> listPersonalTask() {
        return taskMapper.listPersonalTask();
    }

    @Override
    public int changeStatus(String taskId, Integer status) {
        return taskMapper.changeStatus(taskId,status);
    }

    @Override
    public int reviseTask(Task task) {
        return taskMapper.reviseTask(task);
    }

    @Override
    public int deleteTask(String taskId) {
        return taskMapper.deleteTask(taskId);
    }
}
