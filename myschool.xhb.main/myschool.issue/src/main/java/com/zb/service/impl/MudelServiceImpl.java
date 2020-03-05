package com.zb.service.impl;

import com.zb.entity.Mudel;
import com.zb.mapper.MudelMapper;
import com.zb.service.MudelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MudelServiceImpl implements MudelService {
    @Resource
    private MudelMapper mudelMapper;

    //根据功能类型编号获取模板
    @Override
    public List<Mudel> getMudelByTypeId(Integer typeId) {
        return mudelMapper.getMudelByTypeId(typeId);
    }

    // 根据模板编号获取模板信息
    @Override
    public Mudel getMudelById(Integer mudelId) {
        return mudelMapper.getMudelById(mudelId);
    }
}
