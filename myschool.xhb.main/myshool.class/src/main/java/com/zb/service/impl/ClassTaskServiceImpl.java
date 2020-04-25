package com.zb.service.impl;

import com.zb.mapper.ClassTaskMapper;
import com.zb.pojo.ClassTask;
import com.zb.service.ClassTaskService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassTaskServiceImpl implements ClassTaskService {
    @Resource
    private ClassTaskMapper classTaskMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public List<ClassTask> findBeforOneMinuteTaskList() {
        return classTaskMapper.getTaskAll();
    }

    @Override
    public void publish(String id, String exchange, String routingKey) {
        ClassTask taskById = classTaskMapper.findTaskById(id);
        if(taskById!=null){
            rabbitTemplate.convertAndSend(exchange,routingKey,taskById);
            classTaskMapper.updateTaskTime(id);
        }
    }

    @Override
    public int updateTask(String id, Integer version) {
        return classTaskMapper.updateTaskVersion(id,version);
    }

    @Override
    public Integer delTaskByCount() {
        return classTaskMapper.delTaskByCount();
    }

    @Override
    public Integer updateTaskCount(String id) {
        return classTaskMapper.updateTaskCount(id);
    }
}
