package com.zb.service;

import com.zb.pojo.GrowthRecord;
import com.zb.vo.AddRecord;
import com.zb.vo.RetrievalRecord;

import java.util.List;

public interface RecordService {

    List<GrowthRecord> listRecordsByCondition(RetrievalRecord retrievalRecord);
    int addRecord(AddRecord addRecord);
    int addRecordLables(String recordId,Integer lableId);
    String getNewRecordId();
    int deleteRecord(String recordId);
}
