package com.zb.service;

import com.zb.entity.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreService {
    //新增成绩消息
    Score addScore(Score score);

    //根据班级编号获取成绩消息
    List<Score> getScoreListByGradeId(String gradeId);

    //根据消息编号修改消息
    Integer updateScore(Score score);

    //根据成绩编号获取信息
    Score getScoreByScoreId(String scoreId);
}
