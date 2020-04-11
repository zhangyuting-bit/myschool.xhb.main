package com.zb.service;

import com.zb.entity.Score;

public interface ExportService {
    //根据成绩编号获取信息
    Score getScoreByScoreId(String scoreId);
}
