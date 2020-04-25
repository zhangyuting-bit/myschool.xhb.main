package com.zb.mapper;

import com.zb.pojo.ClassTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassTaskMapper {
    //获取全部任务
    List<ClassTask>getTaskAll();

    ClassTask findTaskById(@Param("id")String id);

    Integer addTask(ClassTask classTask);

    Integer delTaskByCount();

    Integer updateTaskTime(@Param("id")String id);

    Integer updateTaskVersion(@Param("id")String id,@Param("version")Integer version);

    Integer updateTaskCount(@Param("id")String id);
}
