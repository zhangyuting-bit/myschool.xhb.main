package com.zb.mapper;

import com.zb.entity.Score;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreMapper {
    ///新增成绩消息
    Integer addScore(Score score);

    //根据班级编号获取成绩消息
    List<Score>getScoreListByGradeId(@Param("gradeId") String gradeId);

    //根据消息编号修改消息
    Integer updateScore(Score score);

    //根据成绩编号获取信息
    Score getScoreByScoreId(@Param("scoreId") String scoreId);

    //修改成绩发送状态
    Integer updateStatus(@Param("scoreId") String scoreId);

    //根据成绩编号撤销成绩
    Integer delScore(@Param("scoreId") String scoreId);
}
