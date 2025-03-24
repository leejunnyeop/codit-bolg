package com.codit.blog.domain.dto.postDto;

import java.util.UUID;

public record PostCreatResponseDto(boolean success,
                                   UUID postId) {
}
