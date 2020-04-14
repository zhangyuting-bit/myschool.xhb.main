package com.zb.mapper;

import com.zb.entity.Grade;
import com.zb.entity.Score;
import com.zb.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreMapper {
    //根据班级编号获取用户信息
    List<User>getUserByGradeId(@Param("gradeId")String gradeId);

    //根据用户编号获取班级信息
    List<Grade>getGradeByUserId(@Param("userId")String userId);

    //根据用户编号查询用户信息
    User getUserByUserId(@Param("userId")String userId);

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
