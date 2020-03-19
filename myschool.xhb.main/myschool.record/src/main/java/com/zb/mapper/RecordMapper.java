package com.zb.mapper;

import com.zb.pojo.GrowthRecord;
import com.zb.vo.AddRecord;
import com.zb.vo.RetrievalRecord;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RecordMapper {

    //根据条件vo检索成长记录
    List<GrowthRecord> listRecordsByCondition(RetrievalRecord retrievalRecord);
    //新增成长记录
    int addRocord(AddRecord addRecord);

}
