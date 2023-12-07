package com.myfreeproject.api.controller;

import com.myfreeproject.api.domain.Post;
import com.myfreeproject.api.request.PostCreate;
import com.myfreeproject.api.service.PostService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

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

        postService.write(params);
        return Map.of();
    }


    @PostMapping("/test/post6")
    public Map<String, String> post6(@RequestBody @Valid PostCreate params){
        return Map.of();
    }


    @PostMapping("/test/post7")
    public void post7(@RequestBody @Valid PostCreate request){
        // db.save(params)
        // 서비스 레이어를 하나만들어서 repository 호출
        // post 요청은 200,201 사용
        //case1. 저장한 데이터 Entity -> response로 응답하기
        //case2. 저장한 데이터의 primary_id -> response로 응답하기
        //      client에서는 수신한 id를 글 조회 API를 통해서 글 데이터를 수신받음
        //case3. 응답 필요 없음 -> 클라이언트에서 모든 POST(글) 데이터 context를 잘 관리함
        //bad case. 서버에서 -> 반드시 이렇게 할거다. (fix)
        //      서버에서는 fix하지말고 유연하게 대응하는게 좋다.
        //      한번에 일괄적으로 잘 처리되는 케이스가 없음 -> 관리가 잘 되는 형태가 중요
        postService.write(request);
    }

    /**
     * /posts -> 글 전체 조회(검색 + 페이징)
     * /posts/{postId} -> 글 한개만 조회
     */

    @GetMapping("/posts/{postId}")
    public Post get(@PathVariable(name = "postId") Long id){
        Post post = postService.get(id);
        return post;
    }

}
