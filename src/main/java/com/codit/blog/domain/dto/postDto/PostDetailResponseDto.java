package com.codit.blog.domain.dto.postDto;

import java.util.List;
import org.springframework.http.HttpStatusCode;

public record PostDetailResponseDto(String title, String content, String nickname, List<String> tags){
}
