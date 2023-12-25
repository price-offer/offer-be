package com.offer.post.application.response;

import com.offer.post.domain.sort.SortItem;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SortResponse {

    private String code;
    private String name;

    @Builder
    public SortResponse(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SortResponse from(SortItem sortItem) {
        return SortResponse.builder()
            .code(sortItem.getCode())
            .name(sortItem.getName())
            .build();
    }
}
