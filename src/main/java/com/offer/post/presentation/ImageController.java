package com.offer.post.presentation;

import com.offer.common.response.ApiResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.post.application.ImageService;
import com.offer.post.application.response.ImageResponse;
import com.offer.post.application.response.ImageUploadResponse;
import com.offer.post.application.response.ImagesUploadResponse;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ImageController {

    private static final int CACHE_CONTROL_MAX_AGE = 30;

    private final ImageService imageService;

    @Operation(summary = "이미지 단건 업로드" , description = "multi-part 형식으로 이미지 전송 시 저장")
    @PostMapping("/api/upload-image")
    public ResponseEntity<ApiResponse<ImageUploadResponse>> uploadImage(@RequestPart MultipartFile file) {
        ImageUploadResponse response = imageService.saveImage(file);

        return ResponseEntity.ok(
                ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "이미지 다건 업로드" , description = "multi-part 형식으로 여러 이미지 전송 시 저장")
    @PostMapping("/api/upload-images")
    public ResponseEntity<ApiResponse<ImagesUploadResponse>> uploadImage(@RequestPart List<MultipartFile> files) {
        ImagesUploadResponse response = imageService.saveImages(files);

        return ResponseEntity.ok(
            ApiResponse.of(ResponseMessage.SUCCESS, response)
        );
    }

    @Operation(summary = "이미지 조회" , description = "")
    @GetMapping("/api/load-image/{path}")
    public ResponseEntity<byte[]> getImage(@PathVariable String path) {
        log.info("/api/load-image/" + path);
        ImageResponse response = imageService.getImage(path);
        return ResponseEntity.ok()
            .cacheControl(CacheControl.maxAge(CACHE_CONTROL_MAX_AGE, TimeUnit.DAYS))
            .contentType(response.getContentType())
            .body(response.getBytes());
    }
}
