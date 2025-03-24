package com.codit.blog.domain.mapper;


import com.codit.blog.domain.dto.userDto.UserCreateResponse;
import com.codit.blog.domain.dto.userDto.UserLoginResponse;
import com.codit.blog.domain.dto.userDto.UserRequestDto;
import com.codit.blog.domain.entity.User;
import java.time.Instant;

public class UserMapper {

    public static User toUser(UserRequestDto userRequestDto, String hashPassword) {
        return User.builder()
                .email(userRequestDto.email())
                .password(hashPassword)
                .nickname(userRequestDto.nickname())
                .createdAt(Instant.now())
                .build();
    }

    public static UserCreateResponse toUserCreateResponse(Boolean check, String message) {
        return UserCreateResponse.builder()
                .success(check)
                .message(message)
                .build();
    }

    public static UserLoginResponse toUserLoginResponse(Boolean check, String token) {
        return new UserLoginResponse(
                check, token
        );
    }

}
