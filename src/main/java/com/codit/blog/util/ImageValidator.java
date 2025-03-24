package com.codit.blog.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ImageValidator {

    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "jpeg", "png", "gif");
    private static final long MAX_FILE_SIZE = 1_048_576; // 1MB
    private static final int MAX_FILENAME_LENGTH = 32;

    public void validate(MultipartFile file) {
        validateExtension(file);
        validateSize(file);
        validateFileNameLength(file);
    }

    private void validateExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("파일명에 확장자가 없습니다.");
        }

        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException("지원하지 않는 이미지 형식입니다.");
        }
    }

    private void validateSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("파일 크기는 1MB 이하만 허용됩니다.");
        }
    }

    private void validateFileNameLength(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String nameWithoutExt = originalFilename.substring(0, originalFilename.lastIndexOf("."));
        if (nameWithoutExt.length() > MAX_FILENAME_LENGTH) {
            throw new IllegalArgumentException("파일명은 최대 32자까지 가능합니다.");
        }
    }
}
