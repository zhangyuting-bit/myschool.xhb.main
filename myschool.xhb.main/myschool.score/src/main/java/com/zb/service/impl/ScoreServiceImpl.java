package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.config.RabbitConfig;
import com.zb.config.RabbitConfigs;
import com.zb.entity.*;
import com.zb.feign.ClassMassagesFeign;
import com.zb.feign.NotOneFeign;
import com.zb.feign.UserFeignClient;
import com.zb.mapper.*;
import com.zb.pojo.Class_add;
import com.zb.pojo.Class_info;
import com.zb.pojo.UserInfo;
import com.zb.service.ScoreService;
import com.zb.util.IdWorker;
import com.zb.util.RedisUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Resource
    private StuCommentMapper stuCommentMapper;

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private ClassMassagesFeign classMassagesFeign;

    @Resource
    private NotOneFeign notOneFeign;

    @Resource
    private NumberMapper numberMapper;

    @Resource
    private SubjectMapper subjectMapper;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private StuSubjectMapper stuSubjectMapper;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisUtil redisUtil;

    //根据token获取用户编号
    public String getUserIdByToken(String token){
        UserInfo userInfo = userFeignClient.getUserInfoByToken(token);
        String userId=userInfo.getId();
        return userId;
    }

    //根据班级编号获取班级信息
    @Cacheable(value = "cache" ,key="#class_number")
    public Class_add getClassInfo(String class_number){
        Class_add class_add=null;
        String key="class_add:"+class_number;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            class_add = JSON.parseObject(o.toString(), Class_add.class);
        }else {
            class_add=classMassagesFeign.showclass(class_number);
            List<Class_info>infoList=classMassagesFeign.classinfo(Integer.parseInt(class_number));
            List<String>userIds=new ArrayList<>();
            for (Class_info class_info:infoList) {
                userIds.add(class_info.getUser_id().toString());
            }
            class_add.setUserIds(userIds);
            redisUtil.set(key,JSON.toJSONString(class_add), 120);
        }
        return class_add;
    }

    //根据分数编号获取集合成绩单例
    @Override
    public Score getByScoreId(String scoreId){
        Score score=null;
        String key="scoreOne:"+scoreId;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            score= JSON.parseObject(o.toString(), Score.class);
        }else {
            score=scoreMapper.getScoreByScoreId(scoreId);
            //根据班级编号获取班级信息
            Class_add class_add=getClassInfo(score.getGradeId());
            score.setClass_add(class_add);
            redisUtil.set(key, JSON.toJSONString(score), 120);
        }
        return score;
    }
    ///根据班级编号获取成绩消息
    @Override
    public List<Score> getScoreListByUserId(Integer typeId,String userId) {
        List<Score>list=new ArrayList<>();
        List<NotOne>ones=notOneFeign.getOneByUserId(typeId,userId);
        for (NotOne notOne : ones) {
            list.add(getByScoreId(notOne.getFunctionId()));
        }
        return list;
    }

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

    //根据消息编号修改消息
    @Override
    public Integer updateScore(Score score) {
        return scoreMapper.updateScore(score);
    }

    //根据成绩编号查询成绩信息
    @Override
    @Cacheable(value = "cache" ,key="#scoreId")
    public Score getScore(String scoreId) {
        Score score=null;
        String key="score:"+scoreId;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            score= JSON.parseObject(o.toString(), Score.class);
        }else {
            score=scoreMapper.getScoreByScoreId(scoreId);
            //根据班级编号获取班级信息
            Class_add class_add=getClassInfo(score.getGradeId());
            score.setClass_add(class_add);
            //根据成绩编号获取全部评论
            List<StuComment>stuComments=stuCommentMapper.getCommentByScoreIdOne(scoreId);
            //根据调查编号获取全部题目
            score.setSubjects(subjectMapper.getSubjectByScoreId(scoreId));
            for (StuComment stuComment:stuComments) {
                //根据学号表编号获取学号表信息
                StuNumber stuNumber=getStuNumber(stuComment.getNumberId());
                List<StuSubject>stuSubjects=new ArrayList<>();
                for (Subject subject:score.getSubjects()) {
                    stuSubjects.add(stuSubjectMapper.getStuSubjectBySubjectAndNumberId(subject.getSubjectId(),stuNumber.getNumberId()));
                }
                stuNumber.setStuSubjects(stuSubjects);
                stuComment.setNumber(stuNumber);
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
    public void sendScore(String scoreId){
        Score score=scoreMapper.getScoreByScoreId(scoreId);
        //根据班级编号获取班级信息
        Class_add class_add=getClassInfo(score.getGradeId());
        score.setClass_add(class_add);
        //根据成绩编号修改发送状态
        scoreMapper.updateStatus(score.getScoreId());
        //根据成绩编号获取科目信息
        List<Subject>subjects=subjectMapper.getSubjectByScoreId(score.getScoreId());
        for (Subject subject:subjects) {
            double sum=0;
            //根据科目编号获取科目成绩
            List<StuSubject>stuSubjects=stuSubjectMapper.getStuSubjectBySubjectId(subject.getSubjectId());
            //根据科目编号获取最高分
            String high=stuSubjectMapper.getStuSubjectHigh(subject.getSubjectId()).getScore();
            //根据科目编号获取最低分
            String low=stuSubjectMapper.getStuSubjectShort(subject.getSubjectId()).getScore();
            for (StuSubject stuSubject:stuSubjects) {
                sum += Double.parseDouble(stuSubject.getScore());
            }
            subject.setHigh(high);
            subject.setLow(low);
            //获取平均分
            subject.setAvg(sum/stuSubjects.size());
            subjectMapper.updateSubject(subject);
        }

        //根据成绩编号获取个人评论信息
        List<StuComment>stuComments=stuCommentMapper.getCommentByScoreId(score.getScoreId());
        for (StuComment stuComment:stuComments) {
            double sum=0;
            for (Subject subject:subjects) {
                //根据学号编号和科目编号获取分数
                StuSubject stuSubject=stuSubjectMapper.getStuSubjectByNumberId(stuComment.getNumberId(),subject.getSubjectId());
                sum += Double.parseDouble(stuSubject.getScore());
            }
            stuComment.setSum(sum);
            stuCommentMapper.updateStuCommentSum(stuComment);
        }

        //根据成绩编号获取评论并排序
        List<StuComment>list=stuCommentMapper.getCommentByScoreIdOne(score.getScoreId());
        int count=0;
        for (StuComment stuComment:list) {
            count++;
            stuComment.setSort(count);
            //根据评论编号修改排名
            stuCommentMapper.updateStuComment(stuComment);
        }

        //根据班级编号获取用户信息
        List<String>userIds=getClassInfo(score.getGradeId()).getUserIds();
        for (String userId : userIds) {
            NotOne notOne = new NotOne();
            notOne.setOneId(IdWorker.getId());
            notOne.setFunctionId(score.getScoreId());
            notOne.setUserId(userId);
            notOne.setTypeId(score.getTypeId());
            notOne.setCreateTime(score.getCreateTime());
            notOneFeign.addNotOne(notOne);
            String key1 = "survey:" + userId + score.getGradeId();
            redisUtil.set(key1, JSON.toJSONString(score), 40);
            String key2 = "ok:" + userId + score.getGradeId();
            String ok = "";
            redisUtil.set(key2, JSON.toJSONString(ok), 40);
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
    public void returnScore(String scoreId){
        Score score1=scoreMapper.getScoreByScoreId(scoreId);
        //根据成绩编号撤销成绩
        scoreMapper.delScore(scoreId);
        //根据成绩编号删除个人成绩
        notOneFeign.delNotOneByNotIdAndUserId(scoreId,score1.getTypeId());
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
        List<String>userIds=getClassInfo(score1.getGradeId()).getUserIds();
        for (String userId : userIds) {
            String key = "score:" + userId + score1.getGradeId();
            if (redisUtil.get(key)!=null){
                Score score=JSON.parseObject(redisUtil.get(key).toString(),Score.class);
                if (score.getScoreId().equals(scoreId)){
                    redisUtil.del(key);
                    String key1= "ok:" + userId + score1.getGradeId();
                    redisUtil.del(key1);
                }
            }

        }
        String key2="delScore:"+score1.getGradeId();
        redisUtil.set(key2,JSON.toJSONString(scoreId),10);

        String key="scoreCount:"+score1.getGradeId();
        //根据班级编号统计成绩表数量
        if (redisUtil.hasKey(key)){
            Object o=redisUtil.get(key);
            Integer count=JSON.parseObject(o.toString(),Integer.class);
            if (count==0){
                count=0;
            }else {
                count--;
            }
            redisUtil.set(key,JSON.toJSONString(count));
        }
        String key1="score:"+scoreId;
        if (redisUtil.hasKey(key1)){
            redisUtil.del(key1);
        }
    }

    //获取修改的考试信息
    @Override
    public Score getUpdateScore(String scoreId){
        Score score=null;
        String key="updateScore:"+scoreId;
        if (redisUtil.hasKey(key)){
            Object o = redisUtil.get(key);
            score= JSON.parseObject(o.toString(), Score.class);
        }else {
            score=scoreMapper.getScoreByScoreId(scoreId);
            //根据成绩编号获取全部评论
            List<StuComment>stuComments=stuCommentMapper.getCommentByScoreIdOne(scoreId);
            //根据调查编号获取全部题目
            score.setSubjects(subjectMapper.getSubjectByScoreId(scoreId));
            for (StuComment stuComment:stuComments) {
                //根据学号表编号获取学号表信息
                StuNumber stuNumber=getStuNumber(stuComment.getNumberId());
                List<StuSubject>stuSubjects=new ArrayList<>();
                for (Subject subject:score.getSubjects()) {
                    StuSubject stuSubject=stuSubjectMapper.getStuSubjectBySubjectAndNumberId(subject.getSubjectId(),stuNumber.getNumberId());
                    stuSubject.setSubject(subject);
                    stuSubjects.add(stuSubject);
                }
                stuNumber.setStuSubjects(stuSubjects);
                stuComment.setNumber(stuNumber);
            }
            score.setStuComments(stuComments);
            redisUtil.set(key, JSON.toJSONString(score), 120);
        }
        return score;
    }

    //修改删除考试信息
    @Override
    public void returnUpdate(String scoreId){
        Score score1=scoreMapper.getScoreByScoreId(scoreId);
        //根据成绩编号删除个人成绩
        notOneFeign.delNotOneByNotIdAndUserId(scoreId,score1.getTypeId());
        //根据成绩编号获取科目信息
        List<Subject>subjects=subjectMapper.getSubjectByScoreId(scoreId);
        for (Subject subject:subjects) {
            //根据科目编号删除科目成绩
            stuSubjectMapper.delStuSubject(subject.getSubjectId());
        }
        //根据成绩编号删除评论
        stuCommentMapper.delCommentByScoreId(scoreId);
        //根据班级编号获取用户信息
        List<String>userIds=getClassInfo(score1.getGradeId()).getUserIds();
        for (String userId : userIds) {
            String key = "score:" + userId + score1.getGradeId();
            if (redisUtil.get(key)!=null){
                Score score=JSON.parseObject(redisUtil.get(key).toString(),Score.class);
                if (score.getScoreId().equals(scoreId)){
                    redisUtil.del(key);
                    String key1= "ok:" + userId + score1.getGradeId();
                    redisUtil.del(key1);
                }
            }
        }
        String key2="delScore:"+score1.getGradeId();
        redisUtil.set(key2,JSON.toJSONString(scoreId),10);
        String key1="score:"+scoreId;
        if (redisUtil.hasKey(key1)){
            redisUtil.del(key1);
        }
        String key="updateScore:"+scoreId;
        if (redisUtil.hasKey(key)){
            redisUtil.del(key);
        }

    }


    //获取撤销信息
    @Override
    public String getScoDelStatus(String gradeId){
        String key="delScore:"+gradeId;
        if (redisUtil.hasKey(key)){
            Object o=redisUtil.get(key);
            String scoreId=JSON.parseObject(o.toString(),String.class);
            return scoreId;
        }
        return null;
    }

    //删除成绩表
    @Override
    public Integer delScore(String scoreId){
        if (scoreMapper.delScore(scoreId)>0){
            return subjectMapper.delSubject(scoreId);
        }
        return null;
    }
//
//    //根据用户编号获取用户信息
//    @Override
//    public User getUserByUserId(String userId) {
//        User user=null;
//        String key="user:"+userId;
//        if (redisUtil.hasKey(key)){
//            Object o=redisUtil.get(key);
//            user=JSON.parseObject(o.toString(),User.class);
//        }else {
//            user=scoreMapper.getUserByUserId(userId);
//            user.setGrades(scoreMapper.getGradeByUserId(userId));
//            redisUtil.set(key,JSON.toJSONString(user),120);
//        }
//        return user;
//    }


    //根据班级编号获取成绩消息
    @Override
    public List<Score> getScoreListByGradeId(String gradeId) {
        return scoreMapper.getScoreListByGradeId(gradeId);
    }

}
