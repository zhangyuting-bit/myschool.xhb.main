package com.zb.mapper;

import com.zb.pojo.GrowthRecord;
import com.zb.vo.AddRecord;
import com.zb.vo.RetrievalRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecordMapper {

    //根据条件vo检索成长记录
    List<GrowthRecord> listRecordsByCondition(RetrievalRecord retrievalRecord);
    //新增成长记录
    int addRecord(AddRecord addRecord);
    //成长记录添加标签
    int addRecordLables(@Param("recordId") String recordId,
                        @Param("lableId") Integer lableId);
    //获取最新的成长记录
    String getNewRecordId();
    //撤销成长记录
    int deleteRecord(@Param("recordId") String recordId);

}
