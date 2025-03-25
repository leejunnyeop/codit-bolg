package com.codit.blog.service.post;

import com.codit.blog.domain.dto.postDto.*;
import com.codit.blog.domain.entity.Post;
import com.codit.blog.domain.entity.User;
import com.codit.blog.domain.mapper.PostMapper;
import com.codit.blog.repository.PostRepository;
import com.codit.blog.util.ImageValidator;
import com.codit.blog.util.PostListUtil;
import com.codit.blog.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserPostServiceImpl implements UserPostService {

    private final PostRepository postRepository;
    private final ImageStorageService imageStorageService;
    private final ImageValidator imageValidator;
    private final UserUtil userUtil;
    private final PostListUtil postListUtil;

    @Override
    public PostCreatResponseDto createPost(String userId, PostCreateRequestDto requestDto, MultipartFile file) {
        User user = userUtil.getUserOrThrow(userId);
        Post post = PostMapper.toPost(requestDto, user.getId());

        if (file != null && !file.isEmpty()) {
            imageValidator.validate(file);
            imageStorageService.save(post.getId(), file);
        }

        postRepository.save(post);
        return new PostCreatResponseDto(true, post.getId());
    }

    @Override
    public PostListResponseDto getPostList(int page, int size) {
        List<Post> paged = postRepository.findPaged(page, size);
        int totalPosts = postRepository.count();
        int totalPages = (int) Math.ceil((double) totalPosts / size);
        return postListUtil.paginateAndMap(paged, totalPages, totalPosts);
    }

    @Override
    public PostDetailResponseDto getPostDetail(String postId) {
        Post post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow(() -> new IllegalArgumentException("없는 게시판 입니다."));
        User user = userUtil.getUserOrThrow(post.getAuthorId().toString());
        return PostMapper.toPostDetailResponse(post, user);
    }

    @Override
    public void updatePost(String userId, String postId, PostUpdateRequestDto requestDto, MultipartFile file) throws IOException {
        Post post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow(() -> new IllegalArgumentException("없는 게시판 입니다."));
        userUtil.validateOwnership(userId, post);

        if (file != null && !file.isEmpty()) {
            imageValidator.validate(file);
            imageStorageService.update(post.getId(), file);
        }

        post.postEdit(requestDto.title(), requestDto.content(), requestDto.tags());
    }

    @Override
    public void deletePost(String userId, String postId) {
        Post post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow(() -> new IllegalArgumentException("없는 게시판 입니다."));
        userUtil.validateOwnership(userId, post);
        postRepository.delete(UUID.fromString(postId));
    }

    @Override
    public PostListResponseDto searchPosts(String keyword, int page, int size) {
        List<Post> filtered = postRepository.findByKeyword(keyword);
        return postListUtil.paginateAndMap(filtered, page, size);
    }

    @Override
    public PostListResponseDto searchByTag(String tag, int page, int size) {
        List<Post> filtered = postRepository.findByTag(tag);
        return postListUtil.paginateAndMap(filtered, page, size);
    }
}
