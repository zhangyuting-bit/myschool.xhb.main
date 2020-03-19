package com.zb.service.impl;

import com.zb.mapper.RecordMapper;
import com.zb.pojo.GrowthRecord;
import com.zb.service.RecordService;
import com.zb.vo.RetrievalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired(required = false)
    RecordMapper recordMapper;

    @Override
    public List<GrowthRecord> listRecordsByCondition(RetrievalRecord retrievalRecord) {
        return recordMapper.listRecordsByCondition(retrievalRecord);
    }
}