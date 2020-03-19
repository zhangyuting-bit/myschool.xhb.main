package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.pojo.GrowthRecord;
import com.zb.pojo.Lable;
import com.zb.service.RecordService;
import com.zb.vo.AddRecord;
import com.zb.vo.RetrievalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@RestController
public class RecordController {

    @Autowired
    RecordService recordService;

    @RequestMapping(value = "/showrecords")
    public List<GrowthRecord> showRecords(@RequestParam(value = "userId", required = false) String userId,
                                          @RequestParam(value = "classId", required = false) String classId,
                                          @RequestParam(value = "visible", defaultValue = "0") Integer visible) {
        RetrievalRecord retrievalRecord = new RetrievalRecord();
        retrievalRecord.setUserId(userId);
        retrievalRecord.setClassId(classId);
        retrievalRecord.setVisible(visible);
        return recordService.listRecordsByCondition(retrievalRecord);
    }


    @RequestMapping(value = "/addrecord")
    public Dto addrecord(@RequestBody AddRecord addRecord) {
        if (addRecord.getVisible() == 1) {
            addRecord.setClassId("0");
        }
        //新增成长记录
        int val = recordService.addRecord(addRecord);

        //获取成长记录id
        Integer recordId = recordService.getMaxRecordId();

        if (addRecord.getLables() != null) {
            String[] split = addRecord.getLables().split(",");
            //成长记录增加标签
            for (String lableId : split) {
                recordService.addRecordLables(recordId, Integer.parseInt(lableId));
            }
        }
        if (val == 1) {
            return DtoUtil.returnSuccess("新增成长记录成功");
        } else {
            return DtoUtil.returnFail("新增失败", "8888");
        }
    }


    @RequestMapping(value = "/deleterecord/{recordId}")
    public Dto deleterecord(@PathVariable("recordId") Integer recordId){
        int val = recordService.deleteRecord(recordId);
        if (val == 1) {
            return DtoUtil.returnSuccess("成长记录撤销成功");
        } else {
            return DtoUtil.returnFail("撤销失败", "8888");
        }
    }

}
