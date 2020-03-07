package com.zb.service.impl;

import com.zb.entity.Mudel;
import com.zb.mapper.MudelMapper;
import com.zb.service.MudelService;
import com.zb.util.RedisUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MudelServiceImpl implements MudelService {
    @Resource
    private MudelMapper mudelMapper;

    @Resource
    private RedisUtil redisUtil;

    //根据功能类型编号获取模板
    @Override
    public List<Mudel> getMudelByTypeId(Integer typeId) {
        return mudelMapper.getMudelByTypeId(typeId);
    }

    // 根据模板编号获取模板信息
    @Override
    @Cacheable(value = "cache" ,key="#mudelId")
    public Mudel getMudelById(Integer mudelId) {
        Mudel mudel=null;
        String key="mudel:"+mudelId;
        if (redisUtil.hasKey(key)){
            Map<Object, Object> hmget = redisUtil.hmget(key);
            mudel.setMudelId(Integer.parseInt(hmget.get("id").toString()));
            mudel.setMudelMessage(hmget.get("mudelMessage").toString());
            mudel.setMudelPic(hmget.get("mudelPic").toString());
            mudel.setMudelTitle(hmget.get("mudelTitle").toString());
            mudel.setTypeId(Integer.parseInt(hmget.get("typeId").toString()));
        }else {
            Map<String,Object>map=new HashMap<>();
            mudel=mudelMapper.getMudelById(mudelId);
            map.put("id",mudel.getMudelId());
            map.put("mudelMessage",mudel.getMudelMessage());
            map.put("mudelPic",mudel.getMudelPic());
            map.put("mudelTitle",mudel.getMudelTitle());
            map.put("typeId",mudel.getTypeId());
            redisUtil.hmset(key,map);
        }
        return mudel;
    }
}
