package com.zb.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zb.feign.UserFeignClient;
import com.zb.pojo.XcUserExt;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtService {
    //密钥
    public static final String SECRET = "sdjhakdhajdklsl;o653632";
    //过期时间:秒
    public static final int EXPIRE = 5;

    @Autowired
    private  UserFeignClient userFeignClient;

    /**
     * 生成Token
     * @param username
     * @return
     * @throws Exception
     */
    public String createToken(String username) throws Exception {
        XcUserExt xcUserExtByUsername = userFeignClient.getXcUserExtByUsername(username);
        String id = xcUserExtByUsername.getId();
        String password = xcUserExtByUsername.getPassword();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.SECOND, EXPIRE);
        Date expireDate = nowTime.getTime();

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        String token = JWT.create()
                .withHeader(map)//头
                .withClaim("id", id)
                .withClaim("username", username)
                .withClaim("phone", xcUserExtByUsername.getPhone())
                .withClaim("codeType", xcUserExtByUsername.getCodeType())
                .withClaim("companyId", xcUserExtByUsername.getCompanyId())
                .withSubject("测试")//
                .withIssuedAt(new Date())//签名时间
                .withExpiresAt(expireDate)//过期时间
                .sign(Algorithm.HMAC256(SECRET));//签名
        return token;
    }

    /**
     * 验证Token
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token)throws Exception{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verifier.verify(token);
        }catch (Exception e){
            throw new RuntimeException("凭证已过期，请重新登录");
        }
        return jwt.getClaims();
    }

    /**
     * 解析Token
     * @param token
     * @return
     */
    public static Map<String, Claim> parseToken(String token){
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaims();
    }

//    public static void main(String[] args) {
//        try {
//            String token = createToken("19826321523");
//            System.out.println("token=" + token);
//            //Thread.sleep(5000);
//            Map<String, Claim> map = verifyToken(token);
//            //Map<String, Claim> map = JwtUtil.parseToken(token);
//            //遍历
//            for (Map.Entry<String, Claim> entry : map.entrySet()) {
//                if (entry.getValue().asString() != null) {
//                    System.out.println(entry.getKey() + "===" + entry.getValue().asString());
//                } else {
//                    System.out.println(entry.getKey() + "===" + entry.getValue().asDate());
//                }
//            }
//
//
////            Map<String, Claim> stringClaimMap = parseToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiLmtYvor5UiLCJ1c2VyTmFtZSI6IndhbmdibyIsImV4cCI6MTU4NDU4NDcxMCwidXNlcklkIjoiMTIzNDUiLCJpYXQiOjE1ODQ1ODQ3MDV9.3pDNg4aPlVhQvFpNtvO5OuAKyvcz8ddf098DOmA0dN8");
////            for (Map.Entry<String, Claim> entry : stringClaimMap.entrySet()) {
////                if (entry.getValue().asString() != null) {
////                    System.out.println(entry.getKey() + "1===" + entry.getValue().asString());
////                } else {
////                    System.out.println(entry.getKey() + "2===" + entry.getValue().asDate());
////                }
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
