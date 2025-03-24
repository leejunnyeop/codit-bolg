package com.codit.blog.service.post;

import com.codit.blog.domain.dto.postDto.PostCreatResponseDto;
import com.codit.blog.domain.dto.postDto.PostCreateRequestDto;
import com.codit.blog.domain.entity.Post;
import com.codit.blog.domain.entity.User;
import com.codit.blog.domain.mapper.PostMapper;
import com.codit.blog.repository.PostRepository;
import com.codit.blog.repository.UserRepository;
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
}
