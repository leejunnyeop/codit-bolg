package com.codit.blog.domain.mapper;

import com.codit.blog.domain.dto.postDto.PostCreateRequestDto;
import com.codit.blog.domain.entity.Post;
import java.time.Instant;
import java.util.UUID;

public class PostMapper {

    public static Post toPost(PostCreateRequestDto postCreateRequestDto, UUID userId) {
        return Post.builder()
                .title(postCreateRequestDto.title())
                .content(postCreateRequestDto.content())
                .tags(postCreateRequestDto.tags())
                .authorId(userId.toString())
                .createdAt(Instant.now())
                .build();

    }
}
