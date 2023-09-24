package com.offer.post.application.response;

import com.offer.post.domain.Post;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class PostDetail {

    private Long id;
    private String title;
    private String description;
    private List<String> imageUrls;
    private int price;
    private String location;
    private String tradeType;
    private String productCondition;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public PostDetail(Long id, String title, String description, List<String> imageUrls, int price,
        String location, String tradeType, String productCondition, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrls = imageUrls;
        this.price = price;
        this.location = location;
        this.tradeType = tradeType;
        this.productCondition = productCondition;
        this.createdAt = createdAt;
    }

    public static PostDetail from(Post post) {
        return PostDetail.builder()
            .id(post.getId())
            .title(post.getTitle())
            .description(post.getDescription())
            .imageUrls(Collections.emptyList())  // TODO: 2023/09/24 NOT IMPLEMENTED YET
            .price(post.getPrice())
            .location(post.getLocation())
            .tradeType(post.getTradeType())
            .productCondition(post.getProductCondition())
            .createdAt(post.getCreatedAt())
            .build();
    }
}
