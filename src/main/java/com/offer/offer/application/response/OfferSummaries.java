package com.offer.offer.application.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OfferSummaries {

    private List<OfferSummary> offers;
    private boolean hasNext;

    @Builder
    public OfferSummaries(List<OfferSummary> offers, boolean hasNext) {
        this.offers = offers;
        this.hasNext = hasNext;
    }
}
