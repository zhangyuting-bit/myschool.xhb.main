package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.Grade;
import com.zb.entity.Score;
import com.zb.entity.User;
import com.zb.service.ScoreService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class ScoreController {
    @Resource
    private ScoreService service;

    ///添加成绩信息
    @RequestMapping("/addScore")
    public Dto<Score> addScore(Score score) {
        return DtoUtil.returnSuccess("ok",service.addScore(score));
    }

    //根据班级编号获取成绩消息
    @GetMapping("/getScoreListByUserId/{userId}")
    public Dto<List<Score>> getScoreListByUserId(@PathVariable("userId") String userId) {
        return DtoUtil.returnSuccess("ok",service.getScoreListByUserId(userId));
    }

    //根据消息编号修改消息
    @RequestMapping("/updateScore")
    public Dto<Integer> updateScore(Score score) {
        return DtoUtil.returnSuccess("ok",service.updateScore(score));
    }

    //根据成绩编号获取信息
    @GetMapping("/getScoreByScoreId/{scoreId}")
    public Dto<Score> getScoreByScoreId(@PathVariable("scoreId") String scoreId) {
        return DtoUtil.returnSuccess("ok",service.getScoreByScoreId(scoreId));
    }

    //推送消息给用户
    @RequestMapping("/sendScore/{scoreId}")
    public void sendScore(@PathVariable("scoreId") String scoreId){
        service.sendScore(scoreId);
    }

    //根据成绩编号查询成绩信息
    @GetMapping("/getScore/{scoreId}")
    public Dto<Score> getScore(@PathVariable("scoreId") String scoreId) {
        return DtoUtil.returnSuccess("ok",service.getScore(scoreId));
    }

    //学生端实时显示信息
    @GetMapping("/getScoStu")
    public Dto<Score> getScoStu(String userId, String gradeId){
        return DtoUtil.returnSuccess("ok",service.getScoStu(userId, gradeId));
    }

    //撤销成绩信息
    @GetMapping("/returnScore")
    public void returnScore(String scoreId,String gradeId){
        service.returnScore(scoreId, gradeId);
    }

    //获取撤销信息
    @GetMapping("/getDelStatus")
    public Dto<String> getDelStatus(String userId,String gradeId){
        return DtoUtil.returnSuccess("ok",service.getDelStatus(userId,gradeId));
    }

    //删除撤销信息
    @GetMapping("/delStatus")
    public void delStatus(String userId,String gradeId){
        service.delStatus(userId, gradeId);
    }

    //删除成绩表
    @GetMapping("/delScore/{scoreId}")
    public Dto<Integer> delScore(@PathVariable("scoreId") String scoreId){
        return DtoUtil.returnSuccess("ok",service.delScore(scoreId));
    }

    //根据用户编号和成绩编号删除成绩信息
    @GetMapping("/delScoreOne")
    public Dto<Integer> delScoreOne(String userId,String scoreId){
        return DtoUtil.returnSuccess("ok",service.delScoreOne(userId, scoreId));
    }

    //根据用户编号获取班级信息
    @GetMapping("/getUserByUserId")
    public User getUserByUserId(String userId){
        return service.getUserByUserId(userId);
    }
}
