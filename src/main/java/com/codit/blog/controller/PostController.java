package com.codit.blog.controller;

import com.codit.blog.domain.dto.postDto.PostCreatResponseDto;
import com.codit.blog.domain.dto.postDto.PostCreateRequestDto;
import com.codit.blog.domain.dto.postDto.PostDetailResponseDto;
import com.codit.blog.domain.dto.postDto.PostListResponseDto;
import com.codit.blog.service.post.UserPostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

GET /api/posts/{postId} - 게시물 상세 조회
PUT /api/posts/{postId} - 게시물 수정 (인증 필요)
DELETE /api/posts/{postId} - 게시물 삭제 (인증 필요)
     */

    @GetMapping()
    public ResponseEntity<PostListResponseDto> getPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        String userId = (String) request.getAttribute("userId");
        PostListResponseDto response = userPostService.getPostList(page, size,userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getDetailPost(@PathVariable String postId) {
        PostDetailResponseDto postDetail = userPostService.getPostDetail(postId);
        return ResponseEntity.ok(postDetail);
    }

}
