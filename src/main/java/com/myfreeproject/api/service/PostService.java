package com.myfreeproject.api.service;

import com.myfreeproject.api.domain.Post;
import com.myfreeproject.api.repository.PostRepository;
import com.myfreeproject.api.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        postRepository.save(post);
    }

}
