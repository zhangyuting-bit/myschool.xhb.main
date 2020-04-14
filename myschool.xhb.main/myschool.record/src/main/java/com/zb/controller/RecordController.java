package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.feign.AgreementFeginClient;
import com.zb.feign.CommentFeginClient;
import com.zb.pojo.*;
import com.zb.service.RecordService;
import com.zb.util.IdWorker;
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

    @Autowired
    AgreementFeginClient agreementFeginClient;

    @Autowired
    CommentFeginClient commentFeginClient;

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
        //随机赋值id
        addRecord.setRecordId(IdWorker.getId());
        if (addRecord.getVisible() == 1) {
            addRecord.setClassId("0");
        }
        //新增成长记录
        int val = recordService.addRecord(addRecord);

        //获取成长记录id
        String recordId = recordService.getNewRecordId();

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
    public Dto deleterecord(@PathVariable("recordId") String recordId) {
        int val = recordService.deleteRecord(recordId);
        if (val == 1) {
            return DtoUtil.returnSuccess("成长记录撤销成功");
        } else {
            return DtoUtil.returnFail("撤销失败", "8888");
        }
    }

    @RequestMapping(value = "/giveagreement")
    public Dto giveagreement(@RequestBody Agreement agreement) {
        agreement.setRecordType(8);
        int val = agreementFeginClient.isgive(agreement);
        if (val == 0) {
            val = agreementFeginClient.giveagreement(agreement);
            if (val == 1) {
                return DtoUtil.returnSuccess("点赞成功");
            } else {
                return DtoUtil.returnFail("失败", "8889");
            }
        } else {
            return DtoUtil.returnFail("重复点赞", "8888");
        }
    }


    @RequestMapping(value = "/commentRecord")
    public Dto commentRecord(@RequestBody Comment comment){
        comment.setRecordType(8);
        int val = commentFeginClient.addcomment(comment);
        if (val == 1) {
            return DtoUtil.returnSuccess("成长记录评论成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }


    @RequestMapping(value = "/showRecordComment")
    public List<Comment> showRecordComment(@RequestParam("recordId") String recordId){
        return commentFeginClient.somecomments(recordId,8);
    }

    @RequestMapping(value = "/deletecomment")
    public Dto deletecomment(@RequestParam("id") String id){
        int val = commentFeginClient.deletecomment(id);
        if (val == 1) {
            return DtoUtil.returnSuccess("删除成长记录评论成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

    @RequestMapping(value = "/addAnswer")
    public Dto addAnswer(@RequestBody Answer answer){
        int val = commentFeginClient.addAnswer(answer);
        if (val == 1) {
            return DtoUtil.returnSuccess("新增回复成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

    @RequestMapping(value = "/listCommentsAnswer")
    public List<Answer> listCommentsAnswer(@RequestParam("commentId") String commentId){
        return commentFeginClient.listCommentsAnswer(commentId);
    }


    @RequestMapping(value = "/deleteAnswer")
    public Dto deleteAnswer(@RequestParam("id") String id){
        int val = commentFeginClient.deleteAnswer(id);
        if (val == 1) {
            return DtoUtil.returnSuccess("删除回复成功");
        } else {
            return DtoUtil.returnFail("失败", "8888");
        }
    }

}
