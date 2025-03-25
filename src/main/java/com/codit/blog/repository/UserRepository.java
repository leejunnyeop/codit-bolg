package com.codit.blog.repository;

import com.codit.blog.domain.entity.User;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends AbstractFileRepository<User> {

    protected UserRepository() {
        super("user.dat");
    }

    public Optional<User> findById(String userId) {
        Map<UUID, User> users = loadAll();
        return Optional.ofNullable(users.get(UUID.fromString(userId)));
    }


//    public List<User> findByAll() {
//        Map<UUID, User> users = loadAll();
//        return users.values().stream()
//                .filter(user -> !user.isDeleted())
//                .collect(Collectors.toList());
//    }

    public Boolean existsById(String userId) {
        Map<UUID, User> users = loadAll();
        Boolean exists = users.containsKey(UUID.fromString(userId));
        return exists;
    }

    public void save(User user) {
        Map<UUID, User> users = loadAll();
        if (users.containsKey(user.getId())) {
            System.out.println("[DEBUG] 동일한 UUID의 데이터가 이미 존재하므로 추가하지 않음: " + user.getId());
        } else {
            users.put(user.getId(), user);
            writeToFile(users);
        }
    }
    public void delete(UUID userId) {
        Map<UUID, User> users = loadAll();
        users.remove(userId);
        writeToFile(users);
    }

    public Optional<User> findByNickname(String nickname) {
        Map<UUID, User> users = loadAll();
        return users.values().stream().filter(user -> user.getNickname().equals(nickname)).findFirst();
    }

    public Optional<User> findByEmail(String email) {
        Map<UUID, User> users = loadAll();
        return users.values().stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    public Optional<User> findByPassword(String password) {
        Map<UUID, User> users = loadAll();
        return users.values().stream().filter(user -> user.getPassword().equals(password)).findFirst();
    }

}
