package com.codit.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
public record UserLoginRequestDto(String uuid, String password) {
}
