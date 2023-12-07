package com.myfreeproject.api.service;

import com.myfreeproject.api.domain.Post;
import com.myfreeproject.api.repository.PostRepository;
import com.myfreeproject.api.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {


    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {
        // postCreate -> Entity

        // 비선호하는 형식
        // post.title = postCreate.getTitle();
        // post.content = postCreate.getContent();

        //선호하는 방식
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }

    public Post get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        return post;

    }
}
