package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.entity.StuComment;
import com.zb.entity.StuNumber;
import com.zb.entity.StuSubject;
import com.zb.entity.Subject;
import com.zb.mapper.StuCommentMapper;
import com.zb.mapper.StuSubjectMapper;
import com.zb.mapper.SubjectMapper;
import com.zb.service.StuCommentService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StuCommentServiceImpl implements StuCommentService {
    @Resource
    private StuCommentMapper stuCommentMapper;

    @Resource
    private ScoreServiceImpl scoreService;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private StuSubjectMapper stuSubjectMapper;

    ///添加个人评论
    @Override
    public Integer addComment(StuComment stuComment) {
        stuComment.setCommentId(IdWorker.getId());
        double sum=0;
        List<StuSubject>subjects=stuSubjectMapper.getStuSubjectByNumberId(stuComment.getNumberId());
        for (StuSubject stuSubject:subjects){
            sum+=Double.parseDouble(stuSubject.getScore());
        }
        stuComment.setSum(sum);
        return stuCommentMapper.addComment(stuComment);
    }

    //根据成绩编号和学号编号获取评论
    @Override
    @Cacheable(value = "cache", key = "#commentId")
    public StuComment getCommentByScoreAndNumberId(String scoreId, String numberId) {
        StuComment stuComment=null;
        String key="comment:"+scoreId+numberId;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            stuComment= JSON.parseObject(o.toString() , StuComment.class);
        }else {
            stuComment=stuCommentMapper.getCommentByScoreAndNumberId(scoreId,numberId);
            stuComment.setNumber(scoreService.getStuNumber(numberId));
            List<Subject>subjects=subjectMapper.getSubjectByScoreId(scoreId);
            for (Subject subject:subjects) {
                subject.setStuSubject(stuSubjectMapper.getStuSubjectBySubjectAndNumberId(subject.getSubjectId(),numberId));
            }
            stuComment.setSubjects(subjects);
            redisUtil.set(key, JSON.toJSONString(stuComment), 120);
        }
        return stuComment;
    }

}
