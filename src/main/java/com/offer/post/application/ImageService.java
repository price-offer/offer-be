package com.offer.post.application;

import com.offer.post.application.response.ImageUploadResponse;
import com.offer.post.domain.ImageFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageService {

    private final Path storagePath;

    public ImageService(@Value("${file.upload-dir}") final String storagePath) {
        this.storagePath = Paths.get(storagePath);
    }

    public ImageUploadResponse saveImage(MultipartFile image) {
        try {
            ImageFile imageFile = ImageFile.from(image);

            String imageFileInputName = imageFile.randomName();
            Path fileStorageLocation = resolvePath(imageFileInputName);
            Files.copy(imageFile.inputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
            return new ImageUploadResponse(imageFileInputName);
        } catch (IOException e) {
            log.error("이미지 업로드 에러", e);
            return null;
        }
    }

    private Path resolvePath(final String imageUrl) {
        return storagePath.resolve(imageUrl);
    }
}
