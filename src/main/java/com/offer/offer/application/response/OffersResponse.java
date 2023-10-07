package com.offer.offer.application.response;

import com.offer.offer.domain.Offer;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OffersResponse {

    private int totalSize;
    private Long postId;
    private List<OfferResponse> offers;
    private Integer offerCountOfCurrentMember;

    @Builder.Default
    private final Integer maximumOfferCount = 2;

    @Builder
    public OffersResponse(int totalSize, Long postId, List<OfferResponse> offers,
        Integer offerCountOfCurrentMember) {
        this.totalSize = totalSize;
        this.postId = postId;
        this.offers = offers;
        this.offerCountOfCurrentMember = offerCountOfCurrentMember;
    }

    public static OffersResponse of(List<Offer> offers, Long postId, Integer offerCountOfCurrentMember) {
        return new OffersResponse(
            offers.size(),
            postId,
            offers.stream()
                .map(OfferResponse::from)
                .collect(Collectors.toList()),
            offerCountOfCurrentMember
        );
    }
}
