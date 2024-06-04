package com.itheima.mp.http;

import cn.hutool.core.codec.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    private static final String SECRET = "secret";

    @Test
    public void generateToken() {

//      JWT头部信息：Header
        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");
//      JWT载荷: Payload
        Map<String, Object> payload = new HashMap<>();
        payload.put("sub", "1234567890");
        payload.put("name","John Doe");
        payload.put("admin",true);
//      声明token失效时间
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, 3000000);

        String token = Jwts.builder()
                .setHeader(header)
                .setClaims(payload)
                .setExpiration(instance.getTime())
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();

        System.out.println(token);
    }


    @Test
    public void getInfoByJwt() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImV4cCI6MTI1MDY4NjAzNDZ9.IuCZfWBEY_hjzQbifs11MZHwaq05O1NyOsN7IwRnYX0";
        JwsHeader header = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getHeader();
        System.out.println(header);
        System.out.println("typ: " + header.get("typ")); // typ: JWT
        System.out.println("typ: " + header.getType()); // typ: JWT

        Claims body = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
        System.out.println(body);
        System.out.println("name: " + body.get("name"));

        String signature = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getSignature();
        System.out.println(signature);
    }

}
