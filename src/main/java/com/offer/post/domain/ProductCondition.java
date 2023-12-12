package com.offer.post.domain;

import java.util.Arrays;

public enum ProductCondition {
    NEW,
    SECONDHAND
    ;

    public static ProductCondition from(String name) {
        return Arrays.stream(values())
            .filter(it -> it.name().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 ProductCondition = " + name));
    }
}
