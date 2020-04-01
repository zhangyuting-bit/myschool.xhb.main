package com.zb.service;

import com.alibaba.fastjson.JSON;
import com.zb.dto.Dto;
import com.zb.dto.DtoUtil;
import com.zb.entity.AuthToken;
import com.zb.pojo.XcUserExt;
import com.zb.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    @Value("${auth.tokenValiditySeconds}")
    private long tokenValiditySeconds;
    @Value("${auth.clientId}")
    private String clientId;
    @Value("${auth.clientSecret}")
    private String clientSecret;
    @Autowired
    private UserFeignClient userFeignClient;

    //置换的时间范围
    private long resetTime = 118 * 60 * 1000;

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    public Dto deleteUserToken(String token){
        if(stringRedisTemplate.hasKey(token)){
            stringRedisTemplate.delete(token);
        }
        return DtoUtil.returnSuccess("ok");
    }

    /**
     * 根据token获取用户信息
     * @return
     */

    public Dto<XcUserExt> getUserJwt(String token){
        XcUserExt xcUserExt=null;
        //根据客户端用户的token去redis中查询
        String json=stringRedisTemplate.opsForValue().get(token).toString();
        AuthToken authToken=JSON.parseObject(json, AuthToken.class);
        if(authToken!=null){
            //jwt中查询用户信息
            String langToken=authToken.getAccess_token();
            String publicKey="-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnASXh9oSvLRLxk901HANYM6KcYMzX8vFPnH/To2R+SrUVw1O9rEX6m1+rIaMzrEKPm12qPjVq3HMXDbRdUaJEXsB7NgGrAhepYAdJnYMizdltLdGsbfyjITUCOvzZ/QgM1M4INPMD+Ce859xse06jnOkCUzinZmasxrmgNV3Db1GtpyHIiGVUY0lSO1Frr9m5dpemylaT0BV3UwTQWVW9ljm6yR3dBncOdDENumT5tGbaDVyClV0FEB1XdSKd7VjiDCDbUAUbDTG1fm3K9sx7kO1uMGElbXLgMfboJ963HEJcU01km7BmFntqI5liyKheX+HBUCD4zbYNPw236U+7QIDAQAB-----END PUBLIC KEY-----";
            Jwt jwt= JwtHelper.decodeAndVerify(langToken,new RsaVerifier(publicKey));
            String data=jwt.getClaims();
            xcUserExt=JSON.parseObject(data, XcUserExt.class);
        }
        return DtoUtil.returnSuccess("ok",xcUserExt);
    }

    public Dto<AuthToken> userlogin(String username, String password) {
        AuthToken authToken = applyAuthToken(username, password);
        String val = addToken(authToken);
        return DtoUtil.returnSuccess("ok", val);
    }

    public String addToken(AuthToken authToken) {
        String key = "user_token:" + authToken.getJti_token();
        stringRedisTemplate.boundValueOps(key).set(JSON.toJSONString(authToken), tokenValiditySeconds, TimeUnit.SECONDS);
        return key;
    }

    /**
     * 生成token
     *
     * @param username 用户的用户名
     * @param pwd      用户的密码
     * @return
     * 发送远程地址给认证创建， 并将返回的结果封装成用户的AutoToken对象
     */

    public AuthToken applyAuthToken(String username, String pwd) {
        AuthToken authToken = new AuthToken();
        String url = "http://localhost:40400/oauth/token";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        String httpbasic = this.httpBasic("XcWebApp", "XcWebApp");
        System.out.println("httpbasic:" + httpbasic);
        headers.add("Authorization", httpbasic);
        MultiValueMap<String, String> bodys = new LinkedMultiValueMap<String, String>();
        bodys.add("grant_type", "password");
        bodys.add("username", username);
        bodys.add("password", pwd);
        bodys.add("redirect_uri", "http://localhost");
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(bodys, headers);
        ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
        Map body = exchange.getBody();
        authToken.setAccess_token(body.get("access_token").toString());
        authToken.setRefresh_token(body.get("refresh_token").toString());
        authToken.setJti_token(body.get("jti").toString());
        return authToken;
    }

    private String httpBasic(String client, String secret) {
        String str = client + ":" + secret;
        byte[] encode = Base64Utils.encode(str.getBytes());
        return "Basic " + new String(encode);
    }

    //置换token,刷新token
    public Dto<String> changetoken(String token) {
        //根据传入token获得AuthToken对象
        String AuthTokenJson = stringRedisTemplate.opsForValue().get(token);
        AuthToken authTokenForRedis = JSON.parseObject(AuthTokenJson, AuthToken.class);
        //获得对象中refresh—_token属性
        String refresh_token = authTokenForRedis.getRefresh_token();
        //重新创建一个AuthToken对象
        AuthToken authToken = new AuthToken();
        //
        String url = "http://localhost:40400/oauth/token";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        String httpbasic = this.httpBasic("XcWebApp", "XcWebApp");
        headers.add("Authorization", httpbasic);
        MultiValueMap<String, String> bodys = new LinkedMultiValueMap<String, String>();
        bodys.add("grant_type", "refresh_token");
        bodys.add("refresh_token", refresh_token);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<MultiValueMap<String, String>>(bodys, headers);
        ResponseEntity<Map> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, Map.class);
        Map body = exchange.getBody();
        authToken.setAccess_token(body.get("access_token").toString());
        authToken.setRefresh_token(body.get("refresh_token").toString());
        authToken.setJti_token(body.get("jti").toString());
        String s = addToken(authToken);
        stringRedisTemplate.delete(token);
        System.out.println("置换成功");
        System.out.println(s);
        return DtoUtil.returnSuccess("ok", s);
    }

    public XcUserExt show(String username){
        XcUserExt user11 = userFeignClient.getXcUserExtByUsername(username);
        return user11;
    }

}
