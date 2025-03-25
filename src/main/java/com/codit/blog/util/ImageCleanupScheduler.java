package com.codit.blog.util;

import com.codit.blog.service.post.ImageStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ImageCleanupScheduler {

    private final ImageStorageService imageStorageService;

    @Scheduled(fixedRate = 60_000)
    public void cleanImages() {
        imageStorageService.cleanup();
    }


}
