package com.zb.service;

import com.zb.entity.Score;

import java.util.List;

public interface ScoreService {
    ///新增成绩消息
    Score addScore(Score score);

    //根据班级编号获取成绩消息
    List<Score> getScoreListByUserId(Integer typeId,String userId);

    //根据用户编号获取用户信息
    User getUserByUserId(String userId);

    //根据消息编号修改消息
    Integer updateScore(Score score);

    //根据成绩编号获取信息
    Score getScoreByScoreId(String scoreId);

    //推送消息给用户
    public void sendScore(String scoreId);

    //根据成绩编号查询成绩信息
    public Score getScore(String scoreId);

    //学生端实时显示信息
    public Score getScoStu(String userId, String gradeId);

    //删除推送消息
    public void delStuSco(String userId, String scoreId, String gradeId);

    //获取推送状态
    public Integer getStatus(String userId, String gradeId);

    //撤销成绩信息
    public void returnScore(String scoreId);

    //获取撤销信息
    public String getScoDelStatus(String gradeId);

    //删除成绩表
    public Integer delScore(String scoreId);

    //修改删除考试信息
    public void returnUpdate(String scoreId);

    //获取修改的考试信息
    public Score getUpdateScore(String scoreId);

    //根据分数编号获取集合成绩单例
    public Score getByScoreId(String scoreId);

}
