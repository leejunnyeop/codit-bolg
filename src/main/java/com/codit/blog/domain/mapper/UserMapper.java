package com.codit.blog.domain.mapper;


import com.codit.blog.domain.dto.UserRequestDto;
import com.codit.blog.domain.entity.User;
import java.time.Instant;

public class UserMapper {

    public static User toUser(UserRequestDto userRequestDto) {
        return User.builder()
                .email(userRequestDto.email())
                .password(userRequestDto.password())
                .nickname(userRequestDto.nickname())
                .createdAt(Instant.now())
                .build();
    }
}
