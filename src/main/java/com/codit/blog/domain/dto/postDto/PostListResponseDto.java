package com.codit.blog.domain.dto.postDto;

import java.util.List;

public record PostListResponseDto(List<PostSummaryDto> posts,
                                  int totalPages,
                                  int currentPage) {
}
