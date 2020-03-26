package com.zb.mapper;

import com.zb.entity.Answer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnswerMapper {
    //根据题目编号获取答案
    List<Answer>getAnswerBySelectId(@Param("selectId")String selectId);

    //添加新答案
    Integer addAnswer(Answer answer);

    //修改答案图片路径
    Integer updateAnswer(@Param("picSrc")String picSrc,@Param("selectId")String selectId);
}
