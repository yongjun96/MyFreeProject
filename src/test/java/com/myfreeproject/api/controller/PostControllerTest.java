package com.myfreeproject.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
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
    private MockMvc mockMvc;

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

        mockMvc.perform(MockMvcRequestBuilders.post("/test/post4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다\", \"content\": \"내용입니다\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World"))
                .andDo(MockMvcResultHandlers.print());

    }

}