package com.offer.post.application;

import com.offer.post.application.response.ImageResponse;
import com.offer.post.application.response.ImageUploadResponse;
import com.offer.post.application.response.ImagesUploadResponse;
import com.offer.post.domain.ImageExtension;
import com.offer.post.domain.ImageFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageService {

    private final Path storagePath;
    private final String imagePathPrefix;

    public ImageService(@Value("${file.upload-dir}") final String storagePath,
        @Value("${file.server-path}") final String imagePathPrefix) {
        this.storagePath = Paths.get(storagePath);
        this.imagePathPrefix = imagePathPrefix;
    }

    public ImageUploadResponse saveImage(MultipartFile file) {
        try {
            ImageFile imageFile = ImageFile.from(file);
            String imageFileInputName = imageFile.randomName();
            Path fileStorageLocation = resolvePath(imageFileInputName);
            Files.copy(imageFile.inputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
            return new ImageUploadResponse(imagePathPrefix + imageFileInputName);
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 업로드 에러. message = " + e.getMessage());
        }
    }

    public ImageResponse getImage(String imageUrl) {
        try {
            Path fileStorageLocation = resolvePath(imageUrl);
            File file = fileStorageLocation.toFile();
            ImageExtension imageExtension = ImageExtension.from(FilenameUtils.getExtension(file.getName()));
            byte[] image = IOUtils.toByteArray(new FileInputStream(file));
            return ImageResponse.of(image, imageExtension.getContentType());
        } catch (Exception e) {
            log.error("파일 조회 실패");
            throw new RuntimeException(e);
        }
    }

    private Path resolvePath(final String imageUrl) {
        return storagePath.resolve(imageUrl);
    }

    public ImagesUploadResponse saveImages(List<MultipartFile> files) {
        try {
            List<String> result = new ArrayList<>();
            for (MultipartFile file : files) {
                ImageFile imageFile = ImageFile.from(file);
                String imageFileInputName = imageFile.randomName();
                Path fileStorageLocation = resolvePath(imageFileInputName);
                Files.copy(imageFile.inputStream(), fileStorageLocation, StandardCopyOption.REPLACE_EXISTING);
                result.add(imagePathPrefix + imageFileInputName);
            }
            return new ImagesUploadResponse(result);
        } catch (IOException e) {
            throw new IllegalArgumentException("이미지 업로드 에러. message = " + e.getMessage());
        }
    }
}
