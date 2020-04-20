package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.feign.AgreementFeginClient;
import com.zb.feign.CommentFeginClient;
import com.zb.pojo.Agreement;
import com.zb.pojo.Answer;
import com.zb.pojo.Comment;
import com.zb.pojo.Habit;
import com.zb.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HabitController {

    @Autowired
    private HabitService habitService;

    @Autowired
    private AgreementFeginClient agreementFeginClient;

    @Autowired
    private CommentFeginClient commentFeginClient;

    @RequestMapping(value = "/addHabit")
    public Dto addHabit(@RequestBody Habit habit){
        int val = habitService.addHabit(habit);
        if (val == 1) {
            return DtoUtil.returnSuccess("新增习惯养成成果成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }


    //查看某天的习惯养成成果
    @RequestMapping(value = "/listHabitsSomeday")
    public List<Habit> listHabitsSomeday(@RequestParam("joinDate") String joinDate,
                                         @RequestParam("subjectId") String subjectId){
        return habitService.listHabitsSomeday(joinDate,subjectId);
    }


    //查看全部习惯成果
    @RequestMapping(value = "/listAllHabits")
    public List<Habit> listAllHabits(@RequestParam("subjectId") String subjectId){
        return habitService.listAllHabits(subjectId);
    }


    //查看某个用户的习惯养成成果
    @RequestMapping(value = "/listSomeoneHabits")
    public List<Habit> listSomeoneHabits(@RequestParam("userId") String userId,
                                         @RequestParam("subjectId") String subjectId){
        return habitService.listSomeoneHabits(userId,subjectId);
    }

    //撤销习惯养成成果
    @RequestMapping(value = "/deleteHabit")
    public Dto deleteHabit(String habitId){
        int val = habitService.deleteHabit(habitId);
        if (val == 1) {
            return DtoUtil.returnSuccess("删除习惯养成成果成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

    //某用户某天是否参加了习惯养成
    @RequestMapping(value = "/isJoinTheHabitSomeDay")
    public Dto isJoinTheHabitSomeDay(@RequestParam("userId") String userId,
                                     @RequestParam("subjectId") String subjectId,
                                     @RequestParam("joinDate") String joinDate){
        int val = habitService.isJoinTheHabitSomeDay(userId,subjectId,joinDate);
        if (val == 1) {
            return DtoUtil.returnSuccess("已参加");
        } else {
            return DtoUtil.returnFail("未参加", "8888");
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
        comment.setRecordType(2);
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
        return commentFeginClient.somecomments(recordId,2);
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
