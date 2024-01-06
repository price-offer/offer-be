package com.offer.review.application.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewCountResponse {

    private final int all;
    private final int seller;
    private final int buyer;
}
