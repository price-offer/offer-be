package com.offer.post.domain;

import java.util.Arrays;

public enum TradeType {
    FACE_TO_FACE("직거래"),
    SHIPPING("택배거래"),
    ALL("직거래/택배거래");

    private final String description;

    TradeType(String description) {
        this.description = description;
    }

    public static TradeType from(String name) {
        return Arrays.stream(values())
            .filter(it -> it.name().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 TradeType = " + name));
    }
}
