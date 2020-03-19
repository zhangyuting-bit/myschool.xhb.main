package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.pojo.GrowthRecord;
import com.zb.service.RecordService;
import com.zb.vo.AddRecord;
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


    @RequestMapping(value = "/addrecord")
    public Dto addrecord(AddRecord addRecord){
        if(addRecord.getVisible() == 1){
            addRecord.setClassId("0");
        }
        if(recordService.addRecord(addRecord) == 1){
            return DtoUtil.returnSuccess("新增成长记录成功");
        }else {
            return DtoUtil.returnFail("新增失败","8888");
        }
    }

}
