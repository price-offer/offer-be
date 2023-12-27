package com.offer.post.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum TradeStatus {
    SELLING("판매중"),
    SOLD("판매완료"),
    UNKNOWN("");

    private final String description;

    TradeStatus(String description) {
        this.description = description;
    }

    public static TradeStatus from(String name) {
        if (name == null) {
            return UNKNOWN;
        }
        return Arrays.stream(values())
            .filter(tradeStatus -> tradeStatus.name().equals(name))
            .findFirst()
            .orElse(UNKNOWN);
    }
}
