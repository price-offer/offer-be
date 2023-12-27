package com.offer.post.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ProductCondition {
    NEW("새상품"),
    SECONDHAND("중고상품");

    private final String description;

    ProductCondition(String description) {
        this.description = description;
    }

    public static ProductCondition from(String name) {
        return Arrays.stream(values())
            .filter(it -> it.name().equals(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("잘못된 ProductCondition = " + name));
    }
}
