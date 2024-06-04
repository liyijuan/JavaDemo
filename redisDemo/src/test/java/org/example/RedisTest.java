package org.example;

import org.example.SpringBootDemoCacheRedisApplicationTests;
import org.example.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.Serializable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Slf4j
public class RedisTest extends SpringBootDemoCacheRedisApplicationTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Serializable> redisCacheTemplate;


    @Test
    public void get() {
//        测试线程安全
        ExecutorService executor = Executors.newFixedThreadPool(1000);
        IntStream.range(0, 1000).forEach(i -> executor.execute(() -> stringRedisTemplate.opsForValue().increment("count", 1)));

        stringRedisTemplate.opsForValue().set("k1", "v1");
        String k1 = stringRedisTemplate.opsForValue().get("k1");
        log.debug("[k1]={}", k1);

        String key = "cache:user:1";
        redisCacheTemplate.opsForValue().set(key, new User(1L, "user1"));

        User user = (User)redisCacheTemplate.opsForValue().get(key);
        log.debug("[user]={}", user);

    }



}
