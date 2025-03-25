package com.codit.blog.util;

import com.codit.blog.domain.entity.Post;
import com.codit.blog.domain.entity.User;
import com.codit.blog.repository.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;

    public void validateEmailNotDuplicate(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다. 확인 부탁드립니다");
        }
    }


    public User getUserOrThrow(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("없는 회원입니다."));
    }

    public User getUserOrThrow(UUID userId) {
        return getUserOrThrow(userId.toString());
    }

    public void validateOwnership(String userId, Post post) {
        if (!post.getAuthorId().equals(userId) || !userRepository.existsById(userId)) {
            throw new IllegalArgumentException("권한이 없습니다");
        }
    }
}
