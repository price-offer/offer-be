package com.offer.post.application.response;

import com.offer.post.domain.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class PostSummary {

    private Long id;
    private String title;
    private int price;
    private String location;
    private String thumbnailImageUrl;
    private boolean liked;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public PostSummary(Long id, String title, int price, String location, String thumbnailImageUrl,
        boolean liked, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.location = location;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.liked = liked;
        this.createdAt = createdAt;
    }

    public static PostSummary from(Post post) {
        return PostSummary.builder()
            .id(post.getId())
            .title(post.getTitle())
            .price(post.getPrice())
            .location(post.getLocation())
            .thumbnailImageUrl(post.getThumbnailImageUrl())
            .liked(false)  // TODO: 2023/09/24 NOT IMPLEMENTED
            .createdAt(post.getCreatedAt())
            .build();
    }
}