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
    private String title;
    private String content;
    private String authorId;
    private List<String> tags;
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

    public void postEdit(String title, String content, List<String> tags){
        if(title != null){
            this.title = title;
        }
        if(content != null){
            this.content = content;
        }
        if(tags != null){
            this.tags = tags;
        }
    }
}
