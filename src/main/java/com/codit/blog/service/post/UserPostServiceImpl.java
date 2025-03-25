package com.codit.blog.service.post;

import com.codit.blog.domain.dto.postDto.*;
import com.codit.blog.domain.entity.Post;
import com.codit.blog.domain.entity.User;
import com.codit.blog.domain.mapper.PostMapper;
import com.codit.blog.repository.PostRepository;
import com.codit.blog.repository.UserRepository;
import com.codit.blog.util.ImageValidator;
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
    private final UserRepository userRepository;
    private final ImageStorageService imageStorageService;
    private final ImageValidator imageValidator;
    private final UserUtil userUtil;

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
        List<PostSummaryDto> postDtos = paged.stream()
                .map(post -> {
                    User author = userUtil.getUserOrThrow(post.getAuthorId());
                    return PostMapper.toSummaryDto(post, author);
                })
                .toList();
        return new PostListResponseDto(postDtos, totalPages, totalPosts);
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

        if (filtered == null || filtered.isEmpty()) {
            throw new IllegalArgumentException("검색한 단어는 없습니다. 다시 검색해주세요");
        }

        int totalPosts = filtered.size();
        int totalPages = (int) Math.ceil((double) totalPosts / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalPosts);
        if (fromIndex >= totalPosts) {
            return new PostListResponseDto(List.of(), totalPages, page);
        }

        List<Post> paged = filtered.subList(fromIndex, toIndex);

        List<PostSummaryDto> postDtos = paged.stream()
                .map(post -> {
                    User author = userUtil.getUserOrThrow(post.getAuthorId());
                    return PostMapper.toSummaryDto(post, author);
                })
                .toList();

        return new PostListResponseDto(postDtos, totalPages, page);
    }

    @Override
    public PostListResponseDto searchByTag(String tag, int page, int size) {
        List<Post> filtered = postRepository.findByTag(tag);

        if (filtered == null || filtered.isEmpty()) {
            throw new IllegalArgumentException("검색한 단어는 없습니다. 다시 검색해주세요");
        }

        int totalPosts = filtered.size();
        int totalPages = (int) Math.ceil((double) totalPosts / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalPosts);
        if (fromIndex >= totalPosts) {
            return new PostListResponseDto(List.of(), totalPages, page);
        }

        List<Post> paged = filtered.subList(fromIndex, toIndex);

        List<PostSummaryDto> postDtos = paged.stream()
                .map(post -> {
                    User author = userUtil.getUserOrThrow(post.getAuthorId());
                    return PostMapper.toSummaryDto(post, author);
                })
                .toList();

        return new PostListResponseDto(postDtos, totalPages, page);
    }
}
