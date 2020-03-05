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

    @Override
    public List<Mudel> getMudelAll(){
        return mudelMapper.getMudelAll();
    }
}
