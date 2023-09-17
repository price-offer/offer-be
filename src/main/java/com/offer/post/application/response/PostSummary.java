package com.offer.post.application.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class PostSummary {

    private String title;
    private int price;
    private String location;
    private String thumbnailImageUrl;
    private boolean liked;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public PostSummary(String title, int price, String location, String thumbnailImageUrl,
        boolean liked, LocalDateTime createdAt) {
        this.title = title;
        this.price = price;
        this.location = location;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.liked = liked;
        this.createdAt = createdAt;
    }
}
