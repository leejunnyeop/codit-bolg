package com.codit.blog.domain.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;

public class PostImage implements Serializable {

    private static final long serialVersionUID = 4L;

    private final UUID id;
    private final UUID postId;   // 단일 FK
    private final UUID imageId;  // 단일 FK

    @Builder
    public PostImage(UUID postId, UUID imageId) {
        this.id = UUID.randomUUID();
        this.postId = postId;
        this.imageId = imageId;
    }
}
