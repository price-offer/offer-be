package com.offer.post.domain;

import java.util.Arrays;

public enum SortType {
    POST,  // 게시글 조회 시 정렬 옵션
    OFFER,  // 가격제안 조회 시 정렬 옵션
    ;

    public static SortType from(String name) {
        return Arrays.stream(values())
            .filter(it -> it.name().equalsIgnoreCase(name))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 SortType. type = " + name));
    }
}
