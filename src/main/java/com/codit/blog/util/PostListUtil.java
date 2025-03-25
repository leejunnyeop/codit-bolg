package com.codit.blog.util;

import com.codit.blog.domain.dto.postDto.PostListResponseDto;
import com.codit.blog.domain.dto.postDto.PostSummaryDto;
import com.codit.blog.domain.entity.Post;
import com.codit.blog.domain.entity.User;
import com.codit.blog.domain.mapper.PostMapper;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostListUtil {

    private final UserUtil userUtil;

    public PostListResponseDto paginateAndMap(List<Post> filtered, int page, int size) {
        if (filtered == null || filtered.isEmpty()) {
            throw new IllegalArgumentException("검색 결과가 없습니다.");
        }

        int totalPosts = filtered.size();
        int totalPages = (int) Math.ceil((double) totalPosts / size);
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, totalPosts);

        if (fromIndex >= totalPosts) {
            return new PostListResponseDto(List.of(), totalPages, page);
        }

        List<Post> paged = filtered.subList(fromIndex, toIndex);
        List<PostSummaryDto> postDtos = mapToSummaryDtos(paged);

        return new PostListResponseDto(postDtos, totalPages, page);
    }

    public List<PostSummaryDto> mapToSummaryDtos(List<Post> posts) {
        return posts.stream()
                .map(post -> {
                    User author = userUtil.getUserOrThrow(post.getAuthorId());
                    return PostMapper.toSummaryDto(post, author);
                })
                .toList();
    }
}
