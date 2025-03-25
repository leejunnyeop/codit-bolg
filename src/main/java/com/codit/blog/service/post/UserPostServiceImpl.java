package com.codit.blog.service.post;

import com.codit.blog.domain.dto.postDto.PostCreatResponseDto;
import com.codit.blog.domain.dto.postDto.PostCreateRequestDto;
import com.codit.blog.domain.dto.postDto.PostDetailResponseDto;
import com.codit.blog.domain.dto.postDto.PostListResponseDto;
import com.codit.blog.domain.dto.postDto.PostSummaryDto;
import com.codit.blog.domain.dto.postDto.PostUpdateRequestDto;
import com.codit.blog.domain.entity.Post;
import com.codit.blog.domain.entity.User;
import com.codit.blog.domain.mapper.PostMapper;
import com.codit.blog.repository.ImageStorageRepository;
import com.codit.blog.repository.PostRepository;
import com.codit.blog.repository.UserRepository;
import com.codit.blog.util.ImageValidator;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserPostServiceImpl implements UserPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageStorageService imageStorageService;
    private final ImageValidator imageValidator;


    @Override
    public PostCreatResponseDto createPost(String userId, PostCreateRequestDto requestDto, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원 입니다. 확인 부탁드립니다"));
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
                    User author = userRepository.findById(post.getAuthorId())
                            .orElseThrow(() -> new IllegalArgumentException("해당 게시판 작성자가 실종되었습니다."));
                    return PostMapper.toSummaryDto(post, author);
                })
                .toList();
        return new PostListResponseDto(postDtos, totalPages, totalPosts);
    }

    @Override
    public PostDetailResponseDto getPostDetail(String postId) {
        Post post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow(() -> new IllegalArgumentException("없는 게시판 입니다."));
        User user = userRepository.findById(post.getAuthorId().toString())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시판 작성자가 실종되었습니다."));
        return PostMapper.toPostDetailResponse(post, user);
    }

    @Override
    public void updatePost(String userId, String postId, PostUpdateRequestDto requestDto, MultipartFile file)throws IOException {
        Post post = postRepository.findById(UUID.fromString(postId))
                .orElseThrow(() -> new IllegalArgumentException("없는 게시판 입니다."));
        if (!post.getAuthorId().equals(userId) || !userRepository.existsById(userId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
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
        if (!post.getAuthorId().equals(userId) || !userRepository.existsById(userId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
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
                    User author = userRepository.findById(post.getAuthorId())
                            .orElseThrow(() -> new IllegalArgumentException("해당 게시판 작성자가 실종되었습니다."));
                    return PostMapper.toSummaryDto(post, author);
                })
                .toList();

        return new PostListResponseDto(postDtos, totalPages, page);
    }



}
