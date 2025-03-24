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




//    public List<Post> findByAll() {
//        Map<UUID, Post> postMap = loadAll();
//        return postMap.values().stream()
//                .collect(Collectors.toList());
//    }

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
//    public void delete(UUID userId) {
//        Map<UUID, User> users = loadAll();
//        users.remove(userId);
//        writeToFile(users);
//    }
//
//    public Optional<User> findByNickname(String nickname) {
//        Map<UUID, User> users = loadAll();
//        return users.values().stream().filter(user -> user.getNickname().equals(nickname)).findFirst();
//    }
//
//    public Optional<User> findByEmail(String email) {
//        Map<UUID, User> users = loadAll();
//        return users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst();
//    }
//
//    public Optional<User> findByPassword(String password) {
//        Map<UUID, User> users = loadAll();
//        return users.values().stream().filter(user -> user.getPassword().equals(password)).findFirst();
//    }
//


}
