package com.offer.offer.application.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.offer.offer.domain.Offer;
import com.offer.post.application.response.EnumResponse;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@JsonInclude(Include.NON_NULL)
public class OfferResponse {

    private Long id;
    private OffererResponse offerer;
    private Integer price;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    private Long messageRoomId;

    @Builder
    public OfferResponse(Long id, OffererResponse offerer, Integer price, LocalDateTime createdAt,
        Long messageRoomId) {
        this.id = id;
        this.offerer = offerer;
        this.price = price;
        this.createdAt = createdAt;
        this.messageRoomId = messageRoomId;
    }

    public static OfferResponse from(Offer offer, Long messageRoomId) {
        return new OfferResponse(offer.getId(),
            new OffererResponse(offer.getOfferer().getId(),
                offer.getOfferer().getNickname(),
                offer.getPost().getLocation(),
                offer.getOfferer().getOfferLevel(),
                new EnumResponse(offer.getTradeType().name(),
                    offer.getTradeType().getDescription()),
                offer.getOfferer().getProfileImageUrl()),
            offer.getPrice(),
            offer.getCreatedAt(),
            messageRoomId);
    }
}
