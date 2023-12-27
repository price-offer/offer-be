package com.offer.offer.application.request;

import com.offer.member.Member;
import com.offer.offer.domain.Offer;
import com.offer.post.domain.Post;
import com.offer.post.domain.TradeType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OfferCreateRequest {

    private Integer price;
    private String tradeType;
    private String location;

    @Builder
    public OfferCreateRequest(Integer price, String tradeType, String location) {
        this.price = price;
        this.tradeType = tradeType;
        this.location = location;
    }

    public Offer toEntity(Member offerer, Post post) {
        return new Offer(post, offerer, this.price, false, TradeType.from(this.tradeType));
    }
}
