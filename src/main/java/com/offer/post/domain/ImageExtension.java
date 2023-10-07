package com.offer.post.domain;

import java.util.Arrays;
import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
public enum ImageExtension {
    JPG("jpg", MediaType.IMAGE_JPEG),
    JPEG("jpeg", MediaType.IMAGE_JPEG),
    PNG("png", MediaType.IMAGE_PNG),
    ;

    private final String extension;
    private final MediaType contentType;

    ImageExtension(String extension, MediaType contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static ImageExtension from(String format) {
        return Arrays.stream(values())
            .filter(imageExtension -> imageExtension.containsType(format))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("이미지 파일 확장자만 들어올 수 있습니다."));
    }

    private boolean containsType(final String format) {
        return extension.equals(format);
    }
}
