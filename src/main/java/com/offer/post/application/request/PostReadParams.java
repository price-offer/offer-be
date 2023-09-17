package com.offer.post.application.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReadParams {

    private String sort;
    private String tradeType;
    private String category;
    private int minPrice;
    private int maxPrice;
}
