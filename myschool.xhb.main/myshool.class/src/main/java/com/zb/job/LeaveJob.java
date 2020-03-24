package com.zb.job;

import com.zb.pojo.Leave_job;
import com.zb.service.ClassleaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LeaveJob {
    @Autowired
    private ClassleaveService classleaveService;
    @Scheduled(cron = "0/5 * * * * *")
    public void send(){
        List<Leave_job> leave_jobs = classleaveService.findleaveBy();
        if(leave_jobs.size()<=0){
            System.out.println("没有请假的人");
        }
        for (Leave_job leaveJob : leave_jobs) {
            classleaveService.findleavenumber(leaveJob.getClass_number());
        }
    }
}
