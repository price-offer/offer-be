package com.offer.post.application.response;

import com.offer.post.domain.Post;
import java.time.LocalDateTime;
import java.util.Set;
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

    @Builder(toBuilder = true)
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

    public static PostSummary from(Post post, Set<Long> likePostIds) {
        boolean liked = false;
        if (likePostIds.contains(post.getId())) {
            liked = true;
        }

        return PostSummary.builder()
            .id(post.getId())
            .title(post.getTitle())
            .price(post.getPrice())
            .location(post.getLocation())
            .thumbnailImageUrl(post.getThumbnailImageUrl())
            .liked(liked)  // TODO: 2023/09/24 NOT IMPLEMENTED
            .createdAt(post.getCreatedAt())
            .build();
    }

    public static PostSummary from(Post post, boolean isLiked) {
        return PostSummary.builder()
                .id(post.getId())
                .title(post.getTitle())
                .price(post.getPrice())
                .location(post.getLocation())
                .thumbnailImageUrl(post.getThumbnailImageUrl())
                .liked(isLiked)
                .createdAt(post.getCreatedAt())
                .build();
    }
}
