package com.myfreeproject.api.controller;

import com.myfreeproject.api.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
public class PostController {

    // SSR -> jsp, thymeleaf, freemarker
    // -> html rendering

    // SPA -> vue, react
    // -> javascript + <-> API (JSON)
    // vue -> vue+SSR = nuxt.js
    // react -> react+SSR = next.js


    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
    // 글등록 -> POST Method


    @GetMapping("/test/get1")
    public String get1() {
        return "Hello World";
    }


    //예전방식 : post1 ~ post3

    @PostMapping("/test/post1")
    public String post1(@RequestParam String title, @RequestParam String content){
        log.info("title={}, content={}", title, content);
        return "Hello World";
    }

    @PostMapping("/test/post2")
    public String post2(@RequestParam Map<String, String> params){
        log.info("params={}", params);
        String title = params.get("title");
        String content = params.get("content");
        return "Hello World";
    }

    @PostMapping("/test/post3")
    public String post3(PostCreate params){
        log.info("params={}", params.toString());
        return "Hello World";
    }


    @PostMapping("/test/post4")
    public String post4(@RequestBody PostCreate params){
        log.info("params={}", params.toString());
        return "Hello World";
    }



}
