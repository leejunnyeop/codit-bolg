package com.codit.blog.domain.dto;

import lombok.Builder;

@Builder
public record UserCreateResponse(Boolean success, String message, String code, String massage) {
}
