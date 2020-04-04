package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.entity.*;
import com.zb.mapper.*;
import com.zb.service.ScoreService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.Number;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private StuCommentMapper stuCommentMapper;

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private ScoreOneMapper scoreOneMapper;

    @Resource
    private NumberMapper numberMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private StuSubjectMapper stuSubjectMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisUtil redisUtil;

    //添加成绩信息
    @Override
    public Score addScore(Score score) {
        score.setScoreId(IdWorker.getId());
        //添加成绩
        scoreMapper.addScore(score);
        String key="scoreCount:"+score.getGradeId();
        //根据班级编号统计成绩表数量
        if (redisUtil.hasKey(key)){
            Object o=redisUtil.get(key);
            Integer count=JSON.parseObject(o.toString(),Integer.class);
            count++;
            redisUtil.set(key,JSON.toJSONString(count));
        }else {
            redisUtil.set(key,JSON.toJSONString(1));
        }
        return score;
    }

    ///根据班级编号获取成绩消息
    @Override
    public List<Score> getScoreListByUserId(String userId) {
        List<Score>list=new ArrayList<>();
        List<ScoreOne>scoreOnes=scoreOneMapper.getScoreListByUserId(userId);
        for (ScoreOne scoreOne : scoreOnes) {
            list.add(getScore(scoreOne.getScoreId()));
        }
        return list;
    }

    //根据消息编号修改消息
    @Override
    public Integer updateScore(Score score) {
        return scoreMapper.updateScore(score);
    }

    //根据成绩编号查询成绩信息
    @Override
    @Cacheable(value = "cache", key = "#scoreId")
    public Score getScore(String scoreId) {
        Score score=null;
        String key="score:"+scoreId;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            score= JSON.parseObject(o.toString(), Score.class);
            System.out.println("redis");
        }else {
            System.out.println("mysql");
            score=scoreMapper.getScoreByScoreId(scoreId);
            //根据teacherId获取老师信息
            ///////////////////////////
            //根据班级编号获取班级信息
            /////////////////////////
            //根据成绩编号获取全部评论
            List<StuComment>stuComments=stuCommentMapper.getCommentByScoreId(scoreId);
            //根据调查编号获取全部题目
            score.setSubjects(subjectMapper.getSubjectByScoreId(scoreId));
            for (StuComment stuComment:stuComments) {
                //根据学号表编号获取学号表信息
                stuComment.setNumber(getStuNumber(stuComment.getNumberId()));
            }
            score.setStuComments(stuComments);
            redisUtil.set(key, JSON.toJSONString(score), 120);
        }
        return score;
    }

    //根据学号编号获取学号信息
    @Cacheable(value = "cache", key = "#numberId")
    public StuNumber getStuNumber(String numberId) {
        StuNumber stuNumber=null;
        String key="number:"+numberId;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            stuNumber= JSON.parseObject(o.toString() ,StuNumber.class);
        }else {
            stuNumber=numberMapper.getNumberByNumberId(numberId);
            redisUtil.set(key, JSON.toJSONString(stuNumber), 120);
        }
        return stuNumber;
    }

    //推送消息给用户
    @Override
    @Transactional
    public void sendScore(String scoreId){
        Score score=scoreMapper.getScoreByScoreId(scoreId);
        //根据成绩编号修改发送状态
        scoreMapper.updateStatus(scoreId);
        List<StuComment>stuComments=stuCommentMapper.getCommentByScoreId(scoreId);
        int count=0;
        for (StuComment stuComment:stuComments) {
            count++;
            stuComment.setSort(count);
            //根据评论编号修改排名
            stuCommentMapper.updateStuComment(stuComment.getSort(),stuComment.getCommentId());
        }
        //根据成绩编号获取科目信息
        List<Subject>subjects=subjectMapper.getSubjectByScoreId(scoreId);

        for (Subject subject:subjects) {
            double sum=0;
            //根据科目编号获取科目成绩
            List<StuSubject>stuSubjects=stuSubjectMapper.getStuSubjectBySubjectId(subject.getSubjectId());
            //根据科目编号获取最高分
            String high=stuSubjectMapper.getStuSubjectHigh(subject.getSubjectId()).getScore();
            //根据科目编号获取最低分
            String low=stuSubjectMapper.getStuSubjectShort(subject.getSubjectId()).getScore();
            for (StuSubject stuSubject:stuSubjects) {
                sum+=Double.parseDouble(stuSubject.getScore());
            }
            subject.setHigh(high);
            subject.setLow(low);
            //获取平均分
            subject.setAvg(sum/stuSubjects.size());
            subjectMapper.updateSubject(subject);
        }
        //根据班级编号获取用户信息
        List<User>users=notificationMapper.getUserByGradeId(score.getGradeId());
        for (User user : users) {
            ScoreOne scoreOne = new ScoreOne();
            scoreOne.setOneId(IdWorker.getId());
            scoreOne.setScoreId(score.getScoreId());
            scoreOne.setUserId(user.getUserId());
            scoreOneMapper.addScoreOne(scoreOne);
            String key = "score:" + user.getUserId() + user.getGradeId();
            redisUtil.set(key, JSON.toJSONString(score), 120);
            String key1= "ok:" + user.getUserId() + user.getGradeId();
            String ok = "";
            redisUtil.set(key1, JSON.toJSONString(ok), 40);
        }
    }

    //根据成绩编号获取信息
    @Override
    public Score getScoreByScoreId(String scoreId) {
        //根据成绩编号获取成绩信息
        Score score = scoreMapper.getScoreByScoreId(scoreId);
        //根据成绩编号获取科目信息
        score.setSubjects(subjectMapper.getSubjectByScoreId(scoreId));
        //根据年级编号获取学员信息
        score.setNumbers(numberMapper.getNumberByGradeId(score.getGradeId()));
        return score;
    }

    //学生端实时显示信息
    @Override
    public Score getScoStu(String userId, String gradeId) {
        String key = "score:" + userId + gradeId;
        Object o = redisUtil.get(key);
        if (o != null) {
            Score score = JSON.parseObject(o.toString(), Score.class);
            return score;
        }
        return null;
    }

    //删除推送消息
    @Override
    public void delStuSco(String userId, String scoreId, String gradeId) {
        String key = "score:" + userId + gradeId;
        Object o = redisUtil.get(key);
        if (o != null) {
            Score score = JSON.parseObject(o.toString(), Score.class);
            if (scoreId.equals(score.getScoreId())) {
                redisUtil.del(key);
                redisUtil.del("ok:" + userId + gradeId);
            }
        }
    }

    //获取推送状态
    @Override
    public Integer getStatus(String userId, String gradeId) {
        String key = "ok:" + userId + gradeId;
        if (redisUtil.hasKey(key)) {
            return 1;
        }
        return null;
    }


    //撤销成绩信息
    @Override
    @Transactional
    public void returnScore(String scoreId,String gradeId){
        //根据成绩编号撤销成绩
        scoreMapper.delScore(scoreId);
        //根据成绩编号删除个人成绩
        scoreOneMapper.delScoreOneByScoreId(scoreId);
        //根据成绩编号获取科目信息
        List<Subject>subjects=subjectMapper.getSubjectByScoreId(scoreId);
        for (Subject subject:subjects) {
            //根据科目编号删除科目成绩
            stuSubjectMapper.delStuSubject(subject.getSubjectId());
        }
        //根据成绩编号删除科目信息
        subjectMapper.delSubject(scoreId);
        //根据成绩编号删除评论
        stuCommentMapper.delCommentByScoreId(scoreId);
        //根据班级编号获取用户信息
        List<User>users=notificationMapper.getUserByGradeId(gradeId);
        for (User user : users) {
            String key = "score:" + user.getUserId() + user.getGradeId();
            redisUtil.del(key);
            String key1= "ok:" + user.getUserId() + user.getGradeId();
            redisUtil.del(key1);
            String key2="delScore:"+user.getUserId()+gradeId;
            redisUtil.set(key2,JSON.toJSONString(scoreId),120);
        }
        String key="scoreCount:"+gradeId;
        //根据班级编号统计成绩表数量
        if (redisUtil.hasKey(key)){
            Object o=redisUtil.get(key);
            Integer count=JSON.parseObject(o.toString(),Integer.class);
            count--;
            redisUtil.set(key,JSON.toJSONString(count));
        }
        String key1="score:"+scoreId;
        if (redisUtil.hasKey(key1)){
            redisUtil.del(key1);
        }

    }

    //获取撤销信息
    @Override
    public String getDelStatus(String userId,String gradeId){
        String key="delScore:"+userId+gradeId;
        if (redisUtil.hasKey(key)){
            Object o=redisUtil.get(key);
            String scoreId=JSON.toJSONString(o.toString());
            return scoreId;
        }
        return null;
    }

    //删除撤销信息
    @Override
    public void delStatus(String userId,String gradeId){
        String key="delScore:"+userId+gradeId;
        redisUtil.del(key);
    }

    //删除成绩表
    @Override
    public Integer delScore(String scoreId){
        return scoreMapper.delScore(scoreId);
    }

    //根据用户编号和成绩编号删除成绩信息
    @Override
    public Integer delScoreOne(String userId,String scoreId){
        return scoreOneMapper.delScoreByUserIdAndScoreId(userId, scoreId);
    }


    //根据用户编号获取用户信息
    @Override
    public User getUserByUserId(String userId) {
        User user=null;
        String key="user:"+userId;
        if (redisUtil.hasKey(key)){
            Object o=redisUtil.get(key);
            user=JSON.parseObject(o.toString(),User.class);
        }else {
            user=notificationMapper.getUserByUserId(userId);
            user.setGrades(notificationMapper.getGradeByUserId(userId));
            redisUtil.set(key,JSON.toJSONString(user),120);
        }
        return user;
    }


}
