package com.codit.blog.service.post;

import com.codit.blog.domain.dto.postDto.PostCreatResponseDto;
import com.codit.blog.domain.dto.postDto.PostCreateRequestDto;
import com.codit.blog.domain.dto.postDto.PostListResponseDto;


public interface UserPostService {

    // 게시물 생성 (작성자 ID 포함)
    PostCreatResponseDto createPost(String userId, PostCreateRequestDto requestDto);

//    // 게시물 목록 조회
    PostListResponseDto getPostList(int page, int size, String userId);
//
//    // 게시물 상세 조회
//    PostDetailResponse getPostDetail(UUID postId, String requesterUserId);
//
//    // 게시물 수정 (작성자 확인 필요)
//    void updatePost(String userId, UUID postId, PostUpdateRequestDto requestDto);
//
//    // 게시물 삭제 (작성자 확인 필요)
//    void deletePost(String userId, UUID postId);
//
//    // 키워드 검색
//    PostListResponse searchPosts(String keyword, int page, int size);
//
//    // 태그 검색
//    PostListResponse searchByTag(String tag, int page, int size);
}
