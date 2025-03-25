package com.codit.blog.domain.dto.userDto;

import lombok.Builder;

@Builder
public record UserCreateResponse(Boolean success, String message, String code, String massage) {
}
