package com.myfreeproject.api.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class PostCreate {

    // @RequestBody에 @Valid를 붙여주면 빈값이 넘어올 때 검증을 해줌
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
