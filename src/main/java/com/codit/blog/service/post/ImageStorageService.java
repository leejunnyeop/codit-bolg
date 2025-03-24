package com.codit.blog.service.post;

import com.codit.blog.domain.entity.Images;
import com.codit.blog.domain.entity.Post;
import com.codit.blog.domain.entity.PostImage;
import com.codit.blog.repository.ImageStorageRepository;
import com.codit.blog.repository.ImagesRepository;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageStorageService {

    private final ImageStorageRepository imageStorageRepository;
    private final ImagesRepository imagesRepository;

    @Value(value = "${upload.image-path}")
    private String uploadDir;

    public void update(UUID postId,MultipartFile file) throws IOException {
        // 게시판 글 찾고
        Optional<PostImage> postImage = imageStorageRepository.findByPostId(postId);
        // 이미지 삭제하고
        imagesRepository.delete(postImage.get().getImageId());
        // 새로운 거 추가
        save(postId, file);
    }

    public void save(UUID postId, MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = extractExtension(originalFilename);
            String fullPath = uploadDir + File.separator + extension;
            file.transferTo(new File(fullPath));
            Images images = new Images(originalFilename, extension, fullPath, file.getSize());
            imagesRepository.save(images);
            PostImage postImage = new PostImage(images.getId(), postId);
            imageStorageRepository.save(postImage);
        } catch (IOException e) {
            throw new RuntimeException("이미지 저장 중 오류 발생", e);
        }
    }

    private String extractExtension(String filename) {
        int lastDot = filename.lastIndexOf(".");
        if (lastDot == -1 || lastDot == filename.length() - 1) {
            throw new IllegalArgumentException("확장자가 없는 파일입니다.");
        }
        return filename.substring(lastDot + 1).toLowerCase();
    }
}

