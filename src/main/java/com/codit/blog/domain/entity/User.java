package com.codit.blog.domain.entity;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private UUID id;
    private String password;
    private String email;
    private String nickname;
    private Instant createdAt;

    @Builder
    public User(String password, String email, String nickname, Instant createdAt) {
        this.id = UUID.randomUUID();
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }
}
