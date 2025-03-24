package com.codit.blog.controller;

import com.codit.blog.domain.dto.postDto.PostCreatResponseDto;
import com.codit.blog.domain.dto.postDto.PostCreateRequestDto;
import com.codit.blog.service.post.UserPostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(" api/posts")
@RequiredArgsConstructor
public class PostController {

    private final UserPostService userPostService;

    @PostMapping()
    public ResponseEntity<PostCreatResponseDto> createPost(
            @RequestBody @Valid PostCreateRequestDto requestDto,
            HttpServletRequest request
    ) {
        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok(userPostService.createPost(userId, requestDto));
    }

    /*
    GET /api/posts?page={page}&size={size} - 게시물 목록 조회
GET /api/posts/{postId} - 게시물 상세 조회
PUT /api/posts/{postId} - 게시물 수정 (인증 필요)
DELETE /api/posts/{postId} - 게시물 삭제 (인증 필요)
     */
}
