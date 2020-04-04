package com.zb.mapper;

import com.zb.entity.Select;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SelectMapper {
    //根据调查编号显示题目
    List<Select>getSelectBySurveyId(@Param("surveyId")String surveyId);

    //添加新题目
    Integer addSelect(Select select);

    //修改音频路径
    Integer updateAudio(@Param("audioSrc")String audioSrc,@Param("surveyId")String surveyId);

    //根据调查编号删除题目信息
    Integer delSelectBySurId(@Param("surveyId")String surveyId);
}
