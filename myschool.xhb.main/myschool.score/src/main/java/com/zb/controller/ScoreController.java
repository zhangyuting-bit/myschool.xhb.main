package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.Score;
import com.zb.service.ScoreService;
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
    @GetMapping("/getScoreListByUserId")
    public Dto<List<Score>> getScoreListByUserId(Integer typeId,String userId) {
        return DtoUtil.returnSuccess("ok",service.getScoreListByUserId(typeId,userId));
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

    //获取推送状态
    @GetMapping("/getScoStatus")
    public Dto<Integer> getStatus(String userId, String gradeId){
        return DtoUtil.returnSuccess("ok",service.getStatus(userId, gradeId));
    }

    //删除推送消息
    @GetMapping("/delStuSco")
    public void delStuSco(String userId, String scoreId, String gradeId){
        service.delStuSco(userId, scoreId, gradeId);
    }

    //撤销成绩信息
    @GetMapping("/returnScore/{scoreId}")
    public void returnScore(@PathVariable("scoreId") String scoreId){
        service.returnScore(scoreId);
    }

    //获取撤销信息
    @GetMapping("/getScoDelStatus/{gradeId}")
    public Dto<String> getDelStatus(@PathVariable("gradeId") String gradeId){
        return DtoUtil.returnSuccess("ok",service.getScoDelStatus(gradeId));
    }

    //删除成绩表
    @GetMapping("/delScore/{scoreId}")
    public Dto<Integer> delScore(@PathVariable("scoreId") String scoreId){
        return DtoUtil.returnSuccess("ok",service.delScore(scoreId));
    }

    //根据用户编号获取班级信息
//    @GetMapping("/getUserByUserId")
//    public User getUserByUserId(String userId){
//        return service.getUserByUserId(userId);
//    }

    //修改删除考试信息
    @GetMapping("/returnUpdate/{scoreId}")
    public void returnUpdate(@PathVariable("scoreId")String scoreId){
        service.returnUpdate(scoreId);
    }

    //获取修改的考试信息
    @GetMapping("/getUpdateScore/{scoreId}")
    public Dto<Score> getUpdateScore(@PathVariable("scoreId") String scoreId){
        return DtoUtil.returnSuccess("ok",service.getUpdateScore(scoreId));
    }

    //根据分数编号获取集合成绩单例
    @GetMapping("/getByScoreId/{scoreId}")
    public Score getByScoreId(@PathVariable("scoreId")String scoreId){
        return service.getByScoreId(scoreId);
    }

    //根据班级编号获取成绩消息
    @GetMapping("/getScoreListByGradeId/{gradeId}")
    public List<Score> getScoreListByGradeId(@PathVariable("gradeId") String gradeId){
        return service.getScoreListByGradeId(gradeId);
    }
}
