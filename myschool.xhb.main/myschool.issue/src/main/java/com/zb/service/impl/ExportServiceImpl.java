package com.zb.service.impl;

import com.zb.entity.Score;
import com.zb.mapper.ScoreMapper;
import com.zb.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Service
public class ExportServiceImpl implements ExportService {

    @Override
    public Score getScoreByScoreId(String scoreId) {
        return null;
    }
}
