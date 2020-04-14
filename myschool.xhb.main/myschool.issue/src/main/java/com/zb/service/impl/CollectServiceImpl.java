package com.zb.service.impl;

import com.zb.entity.Collect;
import com.zb.feign.ScoreCollectFeign;
import com.zb.feign.SurveyCollectFeign;
import com.zb.mapper.CollectMapper;
import com.zb.service.CollectService;
import com.zb.util.IdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Resource
    private CollectMapper collectMapper;

    @Resource
    private NotificationServiceImpl notificationService;

    @Resource
    private SurveyCollectFeign surveyCollectFeign;

    @Resource
    private ScoreCollectFeign scoreCollectFeign;

    //添加收藏
    @Override
    public Integer addCollect(Collect collect) {
        collect.setCollectId(IdWorker.getId());
        return collectMapper.addCollect(collect);
    }

    //删除收藏
    @Override
    public Integer delCollect(String typeId, String userId) {
        return collectMapper.delCollect(typeId, userId);
    }

    //根据用户编号获取收藏信息
    @Override
    public List<Collect> getCollectByUserId(String userId) {
        List<Collect>collects=new ArrayList<>();
        List<Collect>list=collectMapper.getCollectByUserId(userId);
        for (Collect c:list) {
            if (c.getTypeId().equals("1")||c.getTypeId().equals("2")||c.getTypeId().equals("3")||c.getTypeId().equals("4")||c.getTypeId().equals("5")){
                c.setNotification(notificationService.getNotificationByNotId(c.getId()));
                collects.add(c);
            }else if (c.getTypeId().equals("6")){
                c.setSurvey(surveyCollectFeign.getBySurveyId(c.getId()));
                collects.add(c);
            }else if (c.getTypeId().equals("7")){
                c.setScore(scoreCollectFeign.getByScoreId(c.getId()));
                collects.add(c);
            }
        }
        return collects;
    }
}
