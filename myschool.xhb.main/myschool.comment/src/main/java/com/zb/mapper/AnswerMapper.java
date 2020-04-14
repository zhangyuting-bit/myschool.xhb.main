package com.zb.mapper;

import com.zb.pojo.Answer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnswerMapper {

    //新增回答
    int addAnswer(Answer answer);
    //查看评论回答
    List<Answer> listCommentsAnswer(@Param("commentId") String commentId);
    //删除评论回答
    int deleteAnswer(@Param("id") String id);

}
