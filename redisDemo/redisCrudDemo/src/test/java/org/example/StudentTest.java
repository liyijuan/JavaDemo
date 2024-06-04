package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Unit test for simple App.
 */
@Slf4j
@RunWith(SpringRunner.class)
public class StudentTest extends RedisDemoApplicationTest {

    @Autowired
    private RedisTemplate<String, Object> redisUserTemplate;


    @Test
    public void testSet() {
        Student student = new Student(1L, "xxx");
        redisUserTemplate.opsForValue().set("student", student);
    }

    @Test
    public void testKey() {
        System.out.println(redisUserTemplate.opsForValue().get("count"));
    }
}
