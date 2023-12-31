package com.offer.review.domain;

import java.util.Arrays;

public enum Role {
    BUYER, SELLER, ALL

    ;

    public static Role of(String role) {
        return Arrays.stream(values())
            .filter(it -> it.name().equalsIgnoreCase(role))
            .findAny()
            .orElse(ALL);
    }
}
