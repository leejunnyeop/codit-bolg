package com.codit.blog.service.post;

import com.codit.blog.domain.dto.postDto.PostCreatResponseDto;
import com.codit.blog.domain.dto.postDto.PostCreateRequestDto;
import com.codit.blog.domain.dto.postDto.PostListResponseDto;
import com.codit.blog.domain.dto.postDto.PostSummaryDto;
import com.codit.blog.domain.entity.Post;
import com.codit.blog.domain.entity.User;
import com.codit.blog.domain.mapper.PostMapper;
import com.codit.blog.repository.PostRepository;
import com.codit.blog.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPostServiceImpl implements UserPostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public PostCreatResponseDto createPost(String userId, PostCreateRequestDto requestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원 입니다. 확인 부탁드립니다"));
        Post post = PostMapper.toPost(requestDto, user.getId());
        postRepository.save(post);
        return new PostCreatResponseDto(true, post.getId());
    }

    @Override
    public PostListResponseDto getPostList(int page, int size, String userId) {
        List<Post> paged = postRepository.findPaged(page, size);
        int totalPosts = postRepository.count();
        int totalPages = (int) Math.ceil((double) totalPosts / size);
        List<PostSummaryDto> postDtos = paged.stream()
                .map(post -> {
                    User author = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("작성자 없음"));
                    return PostMapper.toSummaryDto(post, author);
                })
                .toList();
        return new PostListResponseDto(postDtos, totalPages, totalPosts);
    }
}
