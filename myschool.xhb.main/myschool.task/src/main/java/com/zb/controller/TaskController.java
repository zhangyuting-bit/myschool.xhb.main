package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.feign.AgreementFeginClient;
import com.zb.feign.CommentFeginClient;
import com.zb.pojo.Agreement;
import com.zb.pojo.Answer;
import com.zb.pojo.Comment;
import com.zb.pojo.Task;
import com.zb.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private AgreementFeginClient agreementFeginClient;

    @Autowired
    private CommentFeginClient commentFeginClient;

    //新增作业成果
    @RequestMapping(value = "/addTask")
    public Dto addTask(@RequestBody Task task) {
        int val = taskService.addTask(task);
        if (val == 1) {
            return DtoUtil.returnSuccess("新增作业成果成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

    //展示全部待批改作业
    @RequestMapping(value = "/listNoCorrectTasks")
    public List<Task> listNoCorrectTasks() {
        return taskService.listNoCorrectTasks();
    }

    //展示全部需订正作业
    @RequestMapping(value = "/listEmendTasks")
    public List<Task> listEmendTasks() {
        return taskService.listEmendTasks();
    }

    //展示全部已通过作业
    @RequestMapping(value = "/listPassdTasks")
    public List<Task> listPassdTasks() {
        return taskService.listPassdTasks();
    }

    //展示全部作业
    @RequestMapping(value = "/listAllTasks")
    public List<Task> listAllTasks() {
        return taskService.listAllTasks();
    }

    //仅展示自己提交的作业
    @RequestMapping(value = "/listPersonalTask")
    public List<Task> listPersonalTask() {
        return taskService.listPersonalTask();
    }

    //修改作业状态(1待批改2需订正3已订正4已通过)
    @RequestMapping(value = "/changeStatus")
    public Dto changeStatus(@RequestParam("taskId") String taskId,
                            @RequestParam("status") Integer status) {
        int val = taskService.changeStatus(taskId, status);
        if (val == 1) {
            return DtoUtil.returnSuccess("修改作业状态成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

    //订正作业
    @RequestMapping(value = "/reviseTask")
    public Dto reviseTask(@RequestBody Task task){
        int val = taskService.reviseTask(task);
        if (val == 1) {
            return DtoUtil.returnSuccess("订正作业成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

    //撤销作业
    @RequestMapping(value = "/deleteTask")
    public Dto deleteTask(@RequestParam("taskId") String taskId) {
        int val = taskService.deleteTask(taskId);
        if (val == 1) {
            return DtoUtil.returnSuccess("撤销作业成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

    //点赞
    @RequestMapping(value = "/giveagreement")
    public Dto giveagreement(@RequestBody Agreement agreement) {
        agreement.setRecordType(4);
        int val = agreementFeginClient.isgive(agreement);
        if (val == 0) {
            val = agreementFeginClient.giveagreement(agreement);
            if (val == 1) {
                return DtoUtil.returnSuccess("点赞成功");
            } else {
                return DtoUtil.returnFail("失败", "8889");
            }
        } else {
            return DtoUtil.returnFail("重复点赞", "8888");
        }
    }


    //评论
    @RequestMapping(value = "/commentTask")
    public Dto commentTask(@RequestBody Comment comment){
        comment.setRecordType(4);
        int val = commentFeginClient.addcomment(comment);
        if (val == 1) {
            return DtoUtil.returnSuccess("成长记录评论成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

    //展示评论
    @RequestMapping(value = "/showTaskComment")
    public List<Comment> showTaskComment(@RequestParam("recordId") String recordId){
        return commentFeginClient.somecomments(recordId,4);
    }

    //删除评论
    @RequestMapping(value = "/deletecomment")
    public Dto deletecomment(@RequestParam("id") String id){
        int val = commentFeginClient.deletecomment(id);
        if (val == 1) {
            return DtoUtil.returnSuccess("删除成长记录评论成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

    //新增评论回复
    @RequestMapping(value = "/addAnswer")
    public Dto addAnswer(@RequestBody Answer answer){
        int val = commentFeginClient.addAnswer(answer);
        if (val == 1) {
            return DtoUtil.returnSuccess("新增回复成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

    //展示评论回复
    @RequestMapping(value = "/listCommentsAnswer")
    public List<Answer> listCommentsAnswer(@RequestParam("commentId") String commentId){
        return commentFeginClient.listCommentsAnswer(commentId);
    }


    //删除评论回复
    @RequestMapping(value = "/deleteAnswer")
    public Dto deleteAnswer(@RequestParam("id") String id){
        int val = commentFeginClient.deleteAnswer(id);
        if (val == 1) {
            return DtoUtil.returnSuccess("删除回复成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

}
