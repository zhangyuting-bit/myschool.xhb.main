package com.zb.mapper;

import com.zb.pojo.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskMapper {

    //新增作业成功
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
    int changeStatus(@Param("taskId") String taskId,
                     @Param("status") Integer status);
    //订正作业
    int reviseTask(Task task);
    //撤销作业
    int deleteTask(@Param("taskId") String taskId);
}
