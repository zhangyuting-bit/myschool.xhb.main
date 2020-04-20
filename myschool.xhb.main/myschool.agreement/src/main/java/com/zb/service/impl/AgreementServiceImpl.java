package com.zb.service.impl;

import com.zb.mapper.AgreementMapper;
import com.zb.pojo.Agreement;
import com.zb.service.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgreementServiceImpl implements AgreementService {

    @Autowired(required = false)
    AgreementMapper agreementMapper;

    @Override
    public int giveAnAgreement(Agreement agreement) {
        return agreementMapper.giveAnAgreement(agreement);
    }

    @Override
    public int countIsGive(Agreement agreement) {
        return agreementMapper.countIsGive(agreement);
    }

    @Override
    public List listUserId(String recordId, Integer recordType) {
        return agreementMapper.getAgreementUser(recordId,recordType);
    }
}
