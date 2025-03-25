package com.codit.blog.repository;


import com.codit.blog.domain.entity.Post;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository extends AbstractFileRepository<Post>{
    protected PostRepository() {
        super("post.dat");
    }

    public Optional<Post> findById(UUID postId) {
        Map<UUID, Post> postMap = loadAll();
        return Optional.ofNullable(postMap.get(postId));
    }

    public List<Post> latestFirst(){
        Map<UUID, Post> postMap = loadAll();
       return postMap.values().stream()
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .collect(Collectors.toList());
    }

    public List<Post> paged(List<Post> posts, int page, int size) {
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, posts.size());
        if (fromIndex >= posts.size()) return List.of();
        return posts.subList(fromIndex, toIndex);
    }

    public List<Post> findPaged(int page, int size) {
        return paged(latestFirst(), page, size);
    }

    public Integer count() {
        Map<UUID, Post> postMap = loadAll();
        return postMap.size();
    }

    public void save(Post post) {
        Map<UUID, Post> postMap = loadAll();
        if (postMap.containsKey(post.getId())) {
            System.out.println("[DEBUG] 동일한 UUID의 데이터가 이미 존재하므로 추가하지 않음: " + post.getId());
        } else {
            postMap.put(post.getId(), post);
            writeToFile(postMap);
        }
    }
    public void delete(UUID postId) {
        Map<UUID,Post> postMap = loadAll();
        postMap.remove(postId);
        writeToFile(postMap);
    }
    public Optional<Post> findByUserId(String userId) {
        Map<UUID, Post> postMap = loadAll();
        return postMap.values().stream().filter(post -> post.getAuthorId().equals(userId)).findFirst();
    }
    public List<Post> findByPostIdAndUserId(String postId, String userId) {
        Map<UUID, Post> postMap = loadAll();
        return postMap.values().stream().filter(post -> post.getAuthorId().equals(userId)).filter(post -> post.getId().equals(postId)).collect(
                Collectors.toList());
    }

    public List<Post> findByKeyword(String keyword) {
        Map<UUID, Post> postMap = loadAll();
        return postMap.values().stream()
                .filter(post -> post.getTitle().toLowerCase().contains(keyword) ||
                post.getContent().toLowerCase().contains(keyword))
                .collect(Collectors.toList());
    }

    public List<Post> findByTag(String tag) {
        Map<UUID, Post> postMap = loadAll();
        return postMap.values().stream()
                .filter(post ->post.getTags().stream()
                        .anyMatch(t -> t.toLowerCase().equals(tag))
                )
        .collect(Collectors.toList());
    }
}
