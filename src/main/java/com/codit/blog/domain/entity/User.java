package com.codit.blog.domain.entity;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
public class User {
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
