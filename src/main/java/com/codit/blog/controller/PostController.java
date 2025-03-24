package com.codit.blog.controller;

import com.codit.blog.domain.dto.postDto.PostCreatResponseDto;
import com.codit.blog.domain.dto.postDto.PostCreateRequestDto;
import com.codit.blog.domain.dto.postDto.PostDetailResponseDto;
import com.codit.blog.domain.dto.postDto.PostListResponseDto;
import com.codit.blog.domain.dto.postDto.PostUpdateRequestDto;
import com.codit.blog.service.post.UserPostService;
import com.codit.blog.util.ImageValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(" api/posts")
@RequiredArgsConstructor
public class PostController {

    private final UserPostService userPostService;

    @PostMapping()
    public ResponseEntity<PostCreatResponseDto> createPost(
            @RequestBody @Valid PostCreateRequestDto requestDto, MultipartFile file,
            HttpServletRequest request
    ) {
        String userId = (String) request.getAttribute("userId");
        return ResponseEntity.ok(userPostService.createPost(userId, requestDto, file));
    }


    @GetMapping()
    public ResponseEntity<PostListResponseDto> getPostList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        String userId = (String) request.getAttribute("userId");
        PostListResponseDto response = userPostService.getPostList(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getDetailPost(@PathVariable String postId) {
        PostDetailResponseDto postDetail = userPostService.getPostDetail(postId);
        return ResponseEntity.ok(postDetail);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(@PathVariable String postId,PostUpdateRequestDto postUpdateRequestDto, HttpServletRequest request,MultipartFile file) {
        String userId = (String) request.getAttribute("userId");
        userPostService.updatePost(userId, postId, postUpdateRequestDto,file);
        return ResponseEntity.ok("회원정보가 수정 완료되었습니다.");
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable String postId, HttpServletRequest request){
        String userId = (String) request.getAttribute("userId");
        userPostService.deletePost(userId, postId);
        return ResponseEntity.noContent().build();
    }
}
