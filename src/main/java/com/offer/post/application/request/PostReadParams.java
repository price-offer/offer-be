package com.offer.post.application.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostReadParams {

    private String sort;
    private String tradeType;
    private String category;
    private Long sellerId;
    private String tradeStatus;
    private int minPrice = 0;
    private int maxPrice = Integer.MAX_VALUE;
    private Long lastId;
    private int limit = 30;
    private String searchKeyword;
}
