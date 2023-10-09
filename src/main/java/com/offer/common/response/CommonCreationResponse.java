package com.offer.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class CommonCreationResponse {
    private final Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdAt;

    private CommonCreationResponse(Long id, LocalDateTime createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public static CommonCreationResponse of(Long id, LocalDateTime createdAt) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(createdAt);

        return new CommonCreationResponse(id, createdAt);
    }
}
