package com.offer.post.presentation;

import com.offer.common.response.ApiResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.post.application.ImageService;
import com.offer.post.application.response.ImageUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse> uploadImage(@RequestParam("image") MultipartFile image) {
        ImageUploadResponse response = imageService.saveImage(image);

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @GetMapping("/api/images/{path}")
    public ResponseEntity<ApiResponse> getImage(@PathVariable String path) {
        // TODO: 2023/10/08 NOT IMPLEMENTED
        return null;
    }
}
