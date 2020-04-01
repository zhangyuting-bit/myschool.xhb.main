package com.zb.feign;

import com.zb.pojo.Agreement;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "AGREEMENTSERVER")
public interface AgreementFeginClient {

    @RequestMapping(value = "/giveagreement")
    public int giveagreement(@RequestBody Agreement agreement);


    @RequestMapping(value = "/isgive")
    public int isgive(@RequestBody Agreement agreement);

}
