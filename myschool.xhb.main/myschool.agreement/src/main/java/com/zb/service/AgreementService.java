package com.zb.service;

import com.zb.pojo.Agreement;

public interface AgreementService {

    int giveAnAgreement(Agreement agreement);
    int countIsGive(Agreement agreement);

}
