package com.zb.job;

import com.zb.pojo.ClassTask;
import com.zb.service.ClassTaskService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class ClassTaskJob {

    @Resource
    private ClassTaskService classTaskService;

    @Scheduled(fixedDelay = 60000)
    public void send(){
        classTaskService.delTaskByCount();
        List<ClassTask> taskList = classTaskService.findBeforOneMinuteTaskList();
        for (ClassTask task : taskList) {
            //锁的机制
            if(classTaskService.updateTask(task.getId(),task.getVersion())>0) {
                classTaskService.publish(task.getId(), task.getMq_exchange(), task.getMq_routingkey());
            }
        }
    }


}
