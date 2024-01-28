package com.offer.post.domain;

import java.util.Arrays;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
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
            .orElseGet(() -> {
                log.warn("잘못된 TradeType = {}", name);
                return ALL;
            });
    }
}
