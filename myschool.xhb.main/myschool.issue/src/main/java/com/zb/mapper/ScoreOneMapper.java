package com.zb.mapper;

import com.zb.entity.ScoreOne;
import org.apache.ibatis.annotations.Param;

public interface ScoreOneMapper {
    //添加个人成绩
    Integer addScoreOne(ScoreOne scoreOne);

    //根据成绩编号删除个人成绩
    Integer delScoreOneByScoreId(@Param("scoreId") String scoreId);
}
