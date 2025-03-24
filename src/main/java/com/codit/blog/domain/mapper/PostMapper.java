package com.codit.blog.domain.mapper;

import com.codit.blog.domain.dto.postDto.PostCreateRequestDto;
import com.codit.blog.domain.dto.postDto.PostSummaryDto;
import com.codit.blog.domain.entity.Post;
import com.codit.blog.domain.entity.User;
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

    public static PostSummaryDto toSummaryDto(Post post, User author) {
        return new PostSummaryDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                author.getId().toString(),
                author.getNickname(),
                post.getCreatedAt(),
                post.getTags()
        );
    }
}
