package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.SpringBootDemoCacheRedisApplicationTests;
import org.example.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class UserServiceTest extends SpringBootDemoCacheRedisApplicationTests {
    @Autowired
    private UserService userService;

    @Test
    public void getTwice() {
        User user1 = userService.get(1L);
        log.debug("user1 = {}", user1);

//        只打印一条get方法的日志，第二次查询没有走数据库而是直接使用了缓存。第二次运行后不再输出get方法中的日志，走了缓存
        User user2 = userService.get(1L);
        log.debug("user2 = {}", user2);
    }

    @Test
    public void getAfterSave() {
        userService.saveOrUpdate(new User(4L, "鸵鸟"));

        User user = userService.get(4L);
        log.debug("user = {}", user);
//        只打印了saveOrUpdate方法中的日志和log.debug的日志，get中的日志未打印。但此处的疑问是为啥
    }

    @Test
    public void deleteUser() {
        userService.get(1L);

        userService.delete(1L);
    }






}