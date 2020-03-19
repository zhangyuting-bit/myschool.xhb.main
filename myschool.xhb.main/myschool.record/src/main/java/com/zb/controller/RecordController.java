package com.zb.controller;

import com.zb.pojo.GrowthRecord;
import com.zb.service.RecordService;
import com.zb.vo.RetrievalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecordController {

    @Autowired
    RecordService recordService;

    @RequestMapping(value = "/showrecords")
    public List<GrowthRecord> showRecords(@RequestParam("userId") String userId,
                                         @RequestParam("classId") String classId,
                                         @RequestParam("visible") Integer visible){
        RetrievalRecord retrievalRecord=new RetrievalRecord();
        retrievalRecord.setUserId(userId);
        retrievalRecord.setClassId(classId);
        retrievalRecord.setIsDelete(visible);
        return recordService.listRecordsByCondition(retrievalRecord);
    }

}
