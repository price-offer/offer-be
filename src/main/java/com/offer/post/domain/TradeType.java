package com.offer.post.domain;

public enum TradeType {
    FACE_TO_FACE("직거래"),
    SHIPPING("택배거래"),
    ALL("직거래/택배거래");

    private final String description;

    TradeType(String description) {
        this.description = description;
    }
}
