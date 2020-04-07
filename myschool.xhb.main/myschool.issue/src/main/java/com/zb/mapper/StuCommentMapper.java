package com.zb.mapper;

import com.zb.entity.StuComment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StuCommentMapper {
    ///添加学生评论
    Integer addComment(StuComment stuComment);

    //根据成绩编号获取评论
    List<StuComment>getCommentByScoreId(@Param("scoreId")String scoreId);

    //根据评论编号修改顺序
    Integer updateStuComment(@Param("sort")Integer sort,@Param("commentId")String commentId);

    //根据成绩编号和学号编号获取评论
    StuComment getCommentByScoreAndNumberId(@Param("scoreId")String scoreId,@Param("numberId")String numberId);

    //根据成绩编号删除评论
    Integer delCommentByScoreId(@Param("scoreId")String scoreId);

    //根据成绩编号获取评论并排序
    List<StuComment>getCommentByScoreIdOne(@Param("scoreId")String scoreId);

    //根据评论编号修改总分
    Integer updateStuCommentSum(@Param("sum")double sum,@Param("commentId")String commentId);
}
