package com.myfreeproject.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myfreeproject.api.domain.Post;
import com.myfreeproject.api.repository.PostRepository;
import com.myfreeproject.api.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//@WebMvcTest 간단한 웹테스트에는 괜찮지만 서비스를 만들고 레퍼지토리를 사용하면 별로임

@SpringBootTest
@AutoConfigureMockMvc // @SpringBootTest에 MockMvc의 빈을 주입할 수 있게 해줌
class PostControllerTest {

    //글 제목
    //글 내용
    //사용자
    //id
    //user
    //level

    /**
     *
     * ------------ json
     *
     * {
     *     "title": "제목",
     *     "content": "내용",
     *     "user": {
     *              "id": "idDate",
     *              "name": "nameData"
     *              }
     * }
     */

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    //다른 테스트가 실행되기 전에 postRepository의 기록을 삭제하고 실행

    /**
     * 매우중요
     */
    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/test/get1 요청시 hello world를 출력한다.")
    void testGet1() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/test/get1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World"))
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    @DisplayName("/test/post1 x-www-form-urlencoded방식을 사용")
    void testPost1() throws Exception{

        //content-Type : application/x-www-form-urlencoded

        mockMvc.perform(MockMvcRequestBuilders.post("/test/post3")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("title", "글제목입니다")
                        .param("content", "글내용입니다")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    @DisplayName("/test/post1 json방식을 사용")
    void testPost2() throws Exception{

        //content-Type : application/json

        mockMvc.perform(MockMvcRequestBuilders.post("/test/post7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다\", \"content\": \"내용입니다\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{}"))
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    @DisplayName("/post 요청시 title값은 필수")
    void testPost3() throws Exception{

        //content-Type : application/json

        mockMvc.perform(MockMvcRequestBuilders.post("/test/post5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용입니다\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                //junit5 jsonPath <-- 검색
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("제목을 입력해주세요."))
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    @DisplayName("/post 요청시 title값은 필수")
    void testPost4() throws Exception{

        //content-Type : application/json

        mockMvc.perform(MockMvcRequestBuilders.post("/test/post6")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용입니다\"}")
                )
                //junit5 jsonPath <-- 검색
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value("제목을 입력해주세요."))
                .andDo(MockMvcResultHandlers.print());

    }


    @Test
    @DisplayName("/post 요청시 DB에 값이 저장된다.")
    void testPost5() throws Exception{
        //content-Type : application/json

        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        //json형태로 가공 **[ObjectMapper 중요]
        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/test/post7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)    // DB에 저장되어야 하는 데이터
                )
                //junit5 jsonPath <-- 검색
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // then
        // 하나의 repository count와 일치해야 합니다.
        Assertions.assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목입니다.", post.getTitle());
        Assertions.assertEquals("내용입니다.", post.getContent());
    }

}
