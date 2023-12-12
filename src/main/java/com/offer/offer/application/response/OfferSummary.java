package com.offer.offer.application.response;

import com.offer.offer.domain.Offer;
import com.offer.post.domain.Post;
import com.offer.post.domain.TradeStatus;
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
    private TradeStatus tradeStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public OfferSummary(Long offerId, Long postId, int offerPrice, String thumbnailImageUrl,
        TradeStatus tradeStatus, LocalDateTime createdAt) {
        this.offerId = offerId;
        this.postId = postId;
        this.offerPrice = offerPrice;
        this.thumbnailImageUrl = thumbnailImageUrl;
        this.tradeStatus = tradeStatus;
        this.createdAt = createdAt;
    }

    public static OfferSummary from(Offer offer) {
        Post post = offer.getPost();
        return new OfferSummary(offer.getId(), post.getId(), offer.getPrice(),
            post.getThumbnailImageUrl(), post.getTradeStatus(), offer.getCreatedAt());
    }
}
