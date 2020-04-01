package com.zb.mapper;

import com.zb.pojo.Agreement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AgreementMapper {

    //点赞
    int giveAnAgreement(Agreement agreement);
    //是否已点赞
    int countIsGive(Agreement agreement);

}
