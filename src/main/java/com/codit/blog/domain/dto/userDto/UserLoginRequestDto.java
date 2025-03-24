package com.codit.blog.domain.dto.userDto;

import lombok.Builder;

@Builder
public record UserLoginRequestDto(String uuid, String password) {
}
