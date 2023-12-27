package com.offer.post.application.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.offer.post.domain.Post;
import com.offer.post.domain.TradeStatus;

import com.offer.review.application.response.ReviewInfoResponse;
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
    private EnumResponse tradeStatus;
    private int likeCount;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private SellerDetail seller;
    private CategoryResponse category;
    private ReviewInfoResponse review;
    private boolean hasReview;

    @Builder(toBuilder = true)
    public PostSummary(Long id, String title, int price, String location, String thumbnailImageUrl,
                       boolean liked, EnumResponse tradeStatus, int likeCount, LocalDateTime createdAt,
                       SellerDetail seller, CategoryResponse category, ReviewInfoResponse review,
        boolean hasReview) {
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
        this.review = review;
        this.hasReview = hasReview;
    }

    public static PostSummary from(Post post, Set<Long> likePostIds, int likeCount,
        ReviewInfoResponse review) {
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
                .liked(liked)
                .tradeStatus(new EnumResponse(post.getTradeStatus().name(), post.getTradeStatus().getDescription()))
                .likeCount(likeCount)
                .createdAt(post.getCreatedAt())
                .review(review)
                .hasReview(review != null)
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
                .tradeStatus(new EnumResponse(post.getTradeStatus().name(), post.getTradeStatus().getDescription()))
                .createdAt(post.getCreatedAt())
                .seller(SellerDetail.from(post.getSeller()))
                .build();
    }
}
