package com.offer.post.application.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.offer.post.domain.Post;
import com.offer.post.domain.TradeStatus;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PostSummary {

    private Long id;
    private String title;
    private int price;
    private String location;
    private String thumbnailImageUrl;
    private boolean liked;
    private TradeStatus tradeStatus;
    private int likeCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private SellerDetail seller;
    private CategoryResponse category;

    @Builder(toBuilder = true)
    public PostSummary(Long id, String title, int price, String location, String thumbnailImageUrl,
                       boolean liked, TradeStatus tradeStatus, int likeCount, LocalDateTime createdAt,
                       SellerDetail seller, CategoryResponse category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.location = location;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.liked = liked;
        this.tradeStatus = tradeStatus;
        this.likeCount = likeCount;
        this.createdAt = createdAt;
        this.seller = seller;
        this.category = category;
    }

    public static PostSummary from(Post post, Set<Long> likePostIds, int likeCount) {
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
                .tradeStatus(post.getTradeStatus())
                .likeCount(likeCount)
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
                .tradeStatus(post.getTradeStatus())
                .createdAt(post.getCreatedAt())
                .seller(SellerDetail.from(post.getSeller()))
                .build();
    }
}
