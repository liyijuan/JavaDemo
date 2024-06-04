package com.itheima.mp.controller;


import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

public class RestTemplateController {


    @GetMapping("/getParam")
    public String getParam(String username) {
        return "get param " + username;
    }

    @PostMapping ("/postParam")
    public String postParam(String username) {
        return "post param " + username;
    }

    @PostMapping ("/postResponse")
    public String postResponse(String username) {
        System.out.println(username);
        return "redirect:/success.html";
    }

    @PostMapping ("isEmpty")
    public String isEmpty() {
        return "已通过并返回:-->isEmpty()方法";
    }

    @GetMapping("requestParam")
    public String requestParam(@RequestParam("id") String id,
                               @RequestParam(value = "age", defaultValue = "1") Integer age) {
        return "已通过并返回:-->requestParam方法入参 id为: " + id + " age为: " + age;
    }

    @PostMapping("requestBody")
    public Map<String, Object> requestBody(@RequestBody Map<String, Object> map) {
        return map;
    }

    @PostMapping("requestBodyAndRequestParam")
    public Map<String, Object> requestBody(@RequestParam("name") String name, @RequestBody Map<String, Object> map) {
        map.put("name", name);
        return map;
    }

    @PostMapping("{name}/restFul/{age}")
    public String restFul(@PathVariable("name") String name, @PathVariable("age") Integer age) {
        return "已通过并返回:-->restFul方法入参 name为: " + name + " age为: " + age;
    }

}
