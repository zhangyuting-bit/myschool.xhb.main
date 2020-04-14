package com.zb.mapper;

import com.zb.entity.ScoreOne;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreOneMapper {
    //添加个人成绩
    Integer addScoreOne(ScoreOne scoreOne);

    //根据成绩编号删除个人成绩
    Integer delScoreOneByScoreId(@Param("scoreId") String scoreId);

    //根据用户编号和成绩编号删除成绩信息
    Integer delScoreByUserIdAndScoreId(@Param("userId") String userId,@Param("scoreId") String scoreId);

    //根据用户查询成绩信息
    List<ScoreOne> getScoreListByUserId(@Param("userId") String userId);
}
