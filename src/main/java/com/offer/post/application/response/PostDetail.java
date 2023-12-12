package com.offer.post.application.response;

import com.offer.post.domain.Post;
import com.offer.post.domain.ProductCondition;
import com.offer.post.domain.TradeStatus;
import com.offer.post.domain.TradeType;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class PostDetail {

    private Long id;
    private String title;
    private String description;
    private String thumbnailImageUrl;
    private List<String> imageUrls;
    private int price;
    private String location;
    private TradeType tradeType;
    private TradeStatus tradeStatus;
    private ProductCondition productCondition;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public PostDetail(Long id, String title, String description, String thumbnailImageUrl, List<String> imageUrls, int price,
        String location, TradeType tradeType, TradeStatus tradeStatus, ProductCondition productCondition, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.imageUrls = imageUrls;
        this.price = price;
        this.location = location;
        this.tradeType = tradeType;
        this.tradeStatus = tradeStatus;
        this.productCondition = productCondition;
        this.createdAt = createdAt;
    }

    public static PostDetail from(Post post) {
        return PostDetail.builder()
            .id(post.getId())
            .title(post.getTitle())
            .description(post.getDescription())
            .thumbnailImageUrl(post.getThumbnailImageUrl())
            .imageUrls(post.getImageUrls())
            .tradeStatus(post.getTradeStatus())
            .price(post.getPrice())
            .location(post.getLocation())
            .tradeType(post.getTradeType())
            .productCondition(post.getProductCondition())
            .createdAt(post.getCreatedAt())
            .build();
    }
}
