package com.zb.mapper;

import com.zb.entity.JobTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JobTaskMapper {
    //获取全部定时任务集合
    List<JobTask>getJobTaskAll();

    //新增定时任务
    Integer addJobTask(JobTask jobTask);

    //根据习惯编号删除定时任务
    Integer delJobTask(@Param("notificationId")String notificationId);

    //根据习惯编号修改提醒
    Integer updateTime(@Param("taskTime")String taskTime,@Param("notificationId")String notificationId);
}
