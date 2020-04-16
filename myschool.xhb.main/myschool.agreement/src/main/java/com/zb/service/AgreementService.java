package com.zb.service;

import com.zb.pojo.Agreement;

import java.util.List;

public interface AgreementService {

    int giveAnAgreement(Agreement agreement);
    int countIsGive(Agreement agreement);
    List listUserId(String recordId, Integer recordType);

}
