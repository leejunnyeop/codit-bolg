package com.codit.blog.repository;


import com.codit.blog.domain.entity.Post;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository extends AbstractFileRepository<Post>{
    protected PostRepository() {
        super("post.dat");
    }

//    public Optional<User> findById(UUID postId) {
//        Map<UUID, User> users = loadAll();
//        return Optional.ofNullable(users.get(UUID.fromString(userId)));
//    }


//    public List<User> findByAll() {
//        Map<UUID, User> users = loadAll();
//        return users.values().stream()
//                .filter(user -> !user.isDeleted())
//                .collect(Collectors.toList());
//    }

//    public Boolean existsById(String userId) {
//        Map<UUID, User> users = loadAll();
//        Boolean exists = users.containsKey(UUID.fromString(userId));
//        return exists;
//    }

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
