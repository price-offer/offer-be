package com.offer.post.application.request;

import lombok.Getter;

@Getter
public class OfferReadParams {

    private String sort;
    private Long lastId;
    private int limit = 10;
}
