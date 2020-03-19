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
    public List<GrowthRecord> showRecords(@RequestParam(value = "userId",required = false) String userId,
                                         @RequestParam(value = "classId",required = false) String classId,
                                         @RequestParam(value = "visible",defaultValue = "0") Integer visible){
        RetrievalRecord retrievalRecord=new RetrievalRecord();
        retrievalRecord.setUserId(userId);
        retrievalRecord.setClassId(classId);
        retrievalRecord.setVisible(visible);
        return recordService.listRecordsByCondition(retrievalRecord);
    }

}
