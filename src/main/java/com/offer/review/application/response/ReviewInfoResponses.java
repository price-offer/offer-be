package com.offer.review.application.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReviewInfoResponses {

    private List<ReviewInfoResponse> reviews;
    private boolean hasNext;

    @Builder
    public ReviewInfoResponses(List<ReviewInfoResponse> reviews, boolean hasNext) {
        this.reviews = reviews;
        this.hasNext = hasNext;
    }
}
