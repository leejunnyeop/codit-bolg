package com.codit.blog.domain.dto.postDto;

import java.util.List;

public record PostUpdateRequestDto(String title, String content, List<String> tags) {
}
