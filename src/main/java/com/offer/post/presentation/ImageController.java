package com.offer.post.presentation;

import com.offer.post.application.ImageService;
import com.offer.post.application.response.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/api/upload-image")
    public ImageUploadResponse uploadImage(@RequestParam("image") MultipartFile image) {
        return imageService.saveImage(image);
    }

    @GetMapping("/api/images/{path}")
    public byte[] getImage(@PathVariable String path) {
        // TODO: 2023/10/08 NOT IMPLEMENTED
        return null;
    }
}
