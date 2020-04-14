package com.zb.service.impl;

import com.zb.mapper.RecordMapper;
import com.zb.pojo.GrowthRecord;
import com.zb.service.RecordService;
import com.zb.vo.AddRecord;
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

    @Override
    public int addRecord(AddRecord addRecord) {
        return recordMapper.addRecord(addRecord);
    }

    @Override
    public int addRecordLables(String recordId, Integer lableId) {
        return recordMapper.addRecordLables(recordId,lableId);
    }

    @Override
    public String getNewRecordId() {
        return recordMapper.getNewRecordId();
    }

    @Override
    public int deleteRecord(String recordId) {
        return recordMapper.deleteRecord(recordId);
    }
}
