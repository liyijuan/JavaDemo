package org.example.controller;


import org.example.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@RestController
public class StudentHandler {

    @Autowired
    private RedisTemplate<String, Object> redisTemplates;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/setStudent")
    public void set(@RequestBody Student student) {
        redisTemplates.opsForValue().set("id", student);
    }

    @GetMapping("/get/{key}")
    public Student get(@PathVariable(name = "key") String key) {
        return (Student) redisTemplates.opsForValue().get(key);
    }

    @GetMapping("/get")
    public void getWithParam(@RequestParam(name = "key") String key) {
        System.out.println(key);
        System.out.println(redisTemplates.opsForValue().get(key));
    }

//    PathVariable中的name为url路径上的key
    @DeleteMapping("/delete/{key0}")
    public boolean delete(@PathVariable(name = "key0") String key) {
        redisTemplates.delete(key);
        return redisTemplates.hasKey(key);
    }

    @GetMapping("/string")
    public String stringTest(){
        redisTemplate.opsForValue().set("str","Hello World");
        String str = (String) redisTemplate.opsForValue().get("str");
        return str;
    }

    @GetMapping("/list")
    public List<String> listTest(){
        ListOperations<String,String> listOperations = redisTemplate.opsForList();
        listOperations.leftPush("list","Hello");
        listOperations.leftPush("list","World");
        listOperations.leftPush("list","Java");
        List<String> list = listOperations.range("list",0,2);
        return list;
    }

    @GetMapping("/set")
    public Set<String> setTest(){
        SetOperations<String,String> setOperations = redisTemplate.opsForSet();
        setOperations.add("set","Hello");
        setOperations.add("set","Hello");
        setOperations.add("set","World");
        setOperations.add("set","World");
        setOperations.add("set","Java");
        setOperations.add("set","Java");
        Set<String> set = setOperations.members("set");
        return set;
    }


    @GetMapping("/zset")
    public Set<String> zsetTest(){
        ZSetOperations<String,String> zSetOperations = redisTemplate.opsForZSet();
        zSetOperations.add("zset","Hello",1);
        zSetOperations.add("zset","World",2);
        zSetOperations.add("zset","Java",3);
        Set<String> set = zSetOperations.range("zset",0,2);
        return set;
    }
}
