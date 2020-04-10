package com.zb.controller;

import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.pojo.Agreement;
import com.zb.service.AgreementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AgreementController {

    @Autowired(required = false)
    AgreementService agreementService;


    @RequestMapping(value = "/giveagreement")
    public int giveagreement(@RequestBody Agreement agreement){
        return agreementService.giveAnAgreement(agreement);
    }

    @RequestMapping(value = "/isgive")
    public int isgive(@RequestBody Agreement agreement){
        return agreementService.countIsGive(agreement);
    }

    @RequestMapping(value = "/allagreementuserid")
    public List allagreementuserid(@RequestParam("recordId") Integer recordId,
                                   @RequestParam("recordType") Integer recordType){
        return agreementService.listUserId(recordId,recordType);
    }

}
