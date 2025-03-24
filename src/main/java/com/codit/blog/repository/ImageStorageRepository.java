package com.codit.blog.repository;

import com.codit.blog.domain.entity.PostImage;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class ImageStorageRepository extends AbstractFileRepository<PostImage> {

    protected ImageStorageRepository() {
        super("imageStore.dat");
    }

    public void save(PostImage postImage) {
        Map<UUID, PostImage> postImageMap = loadAll();
        if (postImageMap.containsKey(postImage.getId())) {
            System.out.println("[DEBUG] 동일한 UUID의 데이터가 이미 존재하므로 추가하지 않음: " + postImage.getId());
        } else {
            postImageMap.put(postImage.getId(), postImage);
            writeToFile(postImageMap);

        }
    }

    public Optional<PostImage> findByPostId(UUID postId) {
        Map<UUID, PostImage> postImageMap = loadAll();
        return postImageMap.values().stream()
                .filter(postImage -> postImage.getPostId().equals(postId))
                .findFirst();
    }

}
