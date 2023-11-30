package com.offer.post.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum TradeStatus {
    SELLING,
    SOLD,
    UNKNOWN
    ;

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
