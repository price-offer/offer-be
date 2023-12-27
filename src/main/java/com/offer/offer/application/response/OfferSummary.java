package com.offer.offer.application.response;

import com.offer.offer.domain.Offer;
import com.offer.post.application.response.EnumResponse;
import com.offer.post.domain.Post;
import com.offer.post.domain.TradeStatus;
import com.offer.review.application.response.ReviewInfoResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
public class OfferSummary {

    private Long offerId;
    private Long postId;
    private int offerPrice;
    private String thumbnailImageUrl;
    private EnumResponse tradeStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private boolean reviewAvailable;
    private boolean hasReview;
    private ReviewInfoResponse review;

    @Builder
    public OfferSummary(Long offerId, Long postId, int offerPrice, String thumbnailImageUrl,
        EnumResponse tradeStatus, LocalDateTime createdAt, boolean reviewAvailable,
        boolean hasReview, ReviewInfoResponse review) {
        this.offerId = offerId;
        this.postId = postId;
        this.offerPrice = offerPrice;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.tradeStatus = tradeStatus;
        this.createdAt = createdAt;
        this.reviewAvailable = reviewAvailable;
        this.hasReview = hasReview;
        this.review = review;
    }

    public static OfferSummary from(Offer offer) {
        Post post = offer.getPost();
        return OfferSummary.builder()
            .offerId(offer.getId())
            .postId(post.getId())
            .offerPrice(offer.getPrice())
            .thumbnailImageUrl(post.getThumbnailImageUrl())
            .tradeStatus(new EnumResponse(post.getTradeStatus().name(), post.getTradeStatus().getDescription()))
            .createdAt(offer.getCreatedAt())
            .build();
    }

    public static OfferSummary from(Offer offer, boolean reviewAvailable, ReviewInfoResponse review) {
        Post post = offer.getPost();
        return OfferSummary.builder()
            .offerId(offer.getId())
            .postId(post.getId())
            .offerPrice(offer.getPrice())
            .thumbnailImageUrl(post.getThumbnailImageUrl())
            .tradeStatus(new EnumResponse(post.getTradeStatus().name(), post.getTradeStatus().getDescription()))
            .createdAt(offer.getCreatedAt())
            .reviewAvailable(reviewAvailable)
            .hasReview(review != null)
            .review(review)
            .build();
    }
}
