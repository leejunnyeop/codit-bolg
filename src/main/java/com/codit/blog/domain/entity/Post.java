package com.codit.blog.domain.entity;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
public class Post implements Serializable {

    private static final long serialVersionUID = 3L;

    private final UUID id;
    private final String title;
    private final String content;
    private final String authorId;
    private final List<String> tags;
    private final Instant createdAt;
    private final Instant updatedAt;

    @Builder
    public Post(String title, String content, String authorId, List<String> tags,
                Instant createdAt) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.tags = tags;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
    }
}
