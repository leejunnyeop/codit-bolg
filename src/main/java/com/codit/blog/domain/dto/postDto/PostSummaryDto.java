package com.codit.blog.domain.dto.postDto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record PostSummaryDto(
        UUID id,
        String title,
        String content,
        String authorId,
        String authorNickname,
        Instant createdAt,
        List<String> tags
) {}