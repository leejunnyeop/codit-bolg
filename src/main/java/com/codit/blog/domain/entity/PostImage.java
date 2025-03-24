package com.codit.blog.domain.entity;

import java.io.Serializable;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostImage implements Serializable {

    private static final long serialVersionUID = 4L;

    private final UUID id;
    private UUID postId;
    private UUID imageId;

    @Builder
    public PostImage(UUID postId, UUID imageId) {
        this.id = UUID.randomUUID();
        this.postId = postId;
        this.imageId = imageId;
    }

    public void updateImageId(UUID imageId, UUID postId) {
        if (postId != null) {
            this.postId = postId;
            this.imageId = imageId;
        }

    }
}
