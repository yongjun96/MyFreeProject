package com.myfreeproject.api.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob // DB에서는 Long텍스트 형태로 생성되도록
    private String content;

    @Builder
    public Post(String title, String content){
        this.title = title;
        this.content = content;
    }

}
