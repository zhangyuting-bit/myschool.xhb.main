package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.entity.Expression;
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
            Object o = redisUtil.get(key);
            mudel= JSON.parseObject(o.toString(), Mudel.class);
        }else {
            mudel=mudelMapper.getMudelById(mudelId);
            redisUtil.set(key, JSON.toJSONString(mudel));
        }
        return mudel;
    }
}
