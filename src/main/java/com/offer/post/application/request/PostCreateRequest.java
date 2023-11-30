package com.offer.post.application.request;

import com.offer.member.Member;
import com.offer.post.domain.Post;
import com.offer.post.domain.TradeStatus;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostCreateRequest {

    private String title;
    private String category;
    private int price;
    private String location;
    private String productCondition; // 상품 상태
    private String tradeType;
    private String thumbnailImageUrl;
    private List<String> imageUrls;
    private String description;

    @Builder
    public PostCreateRequest(String title, String category, int price, String location,
        String productCondition, String tradeType, String thumbnailImageUrl, List<String> imageUrls,
        String description) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.location = location;
        this.productCondition = productCondition;
        this.tradeType = tradeType;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.imageUrls = imageUrls;
        this.description = description;
    }

    public Post toEntity(Member member) {
        return Post.builder()
            .seller(member)
            .title(title)
            .category(category)
            .price(price)
            .location(location)
            .productCondition(productCondition)
            .tradeType(tradeType)
            .thumbnailImageUrl(thumbnailImageUrl)
            .description(description)
            .tradeStatus(TradeStatus.SELLING)
            .build();
    }
}
