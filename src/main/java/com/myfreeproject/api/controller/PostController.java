package com.myfreeproject.api.controller;

import com.myfreeproject.api.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.util.HashMap;
import java.util.List;
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

    @PostMapping("/test/post5")
    public Map<String, String> post5(@RequestBody @Valid PostCreate params, BindingResult result){

        //데이터를 검증하는 이유

        //1. client 개발자가 깜박할 수 있다. 실수로 값을 안보내는 경우
        // --> 검증 부분에서 버그발생 확룔높음
        //2. client bug로 값이 누락
        //3. 외부의 영향으로 임의로 자작된 경우
        //4. DB에 값을 저정할 때 의도치 않은 오류 발생
        //5. 서버 개발자의 편안함을 위해
        //6. 3번 이상의 반복적인 작업은 피해야 한다.
        // --> 자동화 고려

        if(result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            FieldError firstFieldError = fieldErrors.get(0);

            String fieldName = firstFieldError.getField(); // title
            String errorMessage = firstFieldError.getDefaultMessage(); // ..ErrorMessage


            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }
        return Map.of();
    }


    @PostMapping("/test/post6")
    public Map<String, String> post6(@RequestBody @Valid PostCreate params){
        return Map.of();
    }



}
