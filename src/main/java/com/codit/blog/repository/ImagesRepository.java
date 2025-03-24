package com.codit.blog.repository;

import com.codit.blog.domain.entity.Images;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class ImagesRepository extends AbstractFileRepository<Images> {

    protected ImagesRepository() {
        super("images.dat");
    }

    public void save(Images image) {
        Map<UUID, Images> imagesMap = loadAll();
        if (imagesMap.containsKey(image.getId())) {
            System.out.println("[DEBUG] 동일한 UUID의 데이터가 이미 존재하므로 추가하지 않음: " + image.getId());
        } else {
            imagesMap.put(image.getId(), image);
            writeToFile(imagesMap);

        }
    }

    public void delete(UUID id) {
        Map<UUID, Images> imagesMap = loadAll();
        if (imagesMap.containsKey(id)) {
            imagesMap.remove(id);
        }
    }

}
