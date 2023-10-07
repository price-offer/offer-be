package com.offer.offer.application.response;

import com.offer.offer.domain.Offer;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class OfferResponse {

    private Long id;
    private OffererResponse offerer;
    private Integer price;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;

    @Builder
    public OfferResponse(Long id, OffererResponse offerer, Integer price, LocalDateTime createdAt) {
        this.id = id;
        this.offerer = offerer;
        this.price = price;
        this.createdAt = createdAt;
    }

    public static OfferResponse from(Offer offer) {
        return new OfferResponse(offer.getId(),
            new OffererResponse(offer.getOfferer().getId(),
                offer.getOfferer().getNickname(),
                offer.getPost().getLocation(),
                String.valueOf(offer.getOfferer().getOfferLevel()),
                offer.getTradeType(), offer.getOfferer().getProfileImageUrl()),
            offer.getPrice(),
            offer.getCreatedAt()
        );
    }
}
