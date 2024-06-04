package com.itheima.mp.http;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


@SpringBootTest
public class RestTemplateTest {

    /**
     * 涉及到两个技术点：
     * 1. @RequiredArgsConstructor + final 用于spring管理的组件【component及service,repository,controller等】
     * 2. 通过@Bean显式定义交给Sring的注解，需要明确使用@Autowired注解或其他机制注入
     */
    @Autowired
    private RestTemplate restTemplate;

    private static final String GET_URL = "http://localhost:8080/getParam?username=zhangsan";

    @Test
    public void test1() {
        String result = restTemplate.getForObject(GET_URL, String.class);
        System.out.println(result);

    }

    @Test
    public void test2() {
        ResponseEntity<String> entity = restTemplate.getForEntity(GET_URL, String.class);
        System.out.println(entity);
        System.out.println(entity.getClass()); // class org.springframework.http.ResponseEntity
        System.out.println(entity.getBody()); // get param zhangsan
        System.out.println(entity.getStatusCode()); // 200 OK
        System.out.println(entity.getHeaders()); // [Content-Type:"text/plain;charset=UTF-8", Content-Length:"18", Date:"Tue, 30 Jan 2024 13:29:31 GMT", Keep-Alive:"timeout=60", Connection:"keep-alive"]
        System.out.println(entity.getStatusCodeValue()); // 200
    }

    @Test
    public void test3() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "en");
        String ans = restTemplate.getForObject(GET_URL, String.class, map);
        System.out.println(ans);
    }

    @Test
    public void isEmpty() throws URISyntaxException {
        String url = "http://127.0.0.1:8080/isEmpty";
        ResponseEntity<String> exchange = restTemplate.exchange(new RequestEntity<String>(HttpMethod.POST, new URI(url)), String.class);
        String body = exchange.getBody();
        System.err.println(body);
    }

    /**
     * 入参为@RequestParam
     */
    @Test
    public void requestParam() throws URISyntaxException {
        String url = "http://127.0.0.1:8080/requestParam";
        ResponseEntity<String> exchange = restTemplate
                .exchange(new RequestEntity<String>(
                        HttpMethod.GET,
                        new URI(url + "?id=123&age=13")),
                        String.class);
        String body = exchange.getBody();
        System.err.println(body);
    }

    @Test
    public void requestBody() throws URISyntaxException {
        String url = "http://127.0.0.1:8080/requestBody";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
        headers.add("content-type", "application/json");
        Map<String, Object> body = new HashMap<>();
        body.put("page", 1);
        body.put("size", 5);
        body.put("mhh", "2");
        body.put("小白老师", 0);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSONUtil.toJsonStr(body), headers);

        ResponseEntity<String> exchange = restTemplate
                .exchange(url, HttpMethod.POST, httpEntity, String.class);
        String ans = exchange.getBody();
        System.err.println(ans);
    }

    @Test
    public void requestBodyAndRequestParam() {
        String url = "http://127.0.0.1:8080/requestBodyAndRequestParam";
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("content-type", "application/json");
        HashMap<String, Object> body = new HashMap<>();
        body.put("teacher", 0);
        HttpEntity<String> httpEntity = new HttpEntity<>(JSONUtil.toJsonStr(body), headers);

        ResponseEntity<String> exchange = restTemplate
                .exchange(url + "?name=mmm", HttpMethod.POST, httpEntity, String.class);
        String ans = exchange.getBody();
        System.out.println(ans);
    }

    @Test
    public void restFul() {
        String url = "http://127.0.0.1:8080/{name}/restFul/{age}";
        // 配置请求体
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body);

        String s = restTemplate.postForObject(url, entity, String.class, "hna", "12");
        System.out.println(s);
    }




}
