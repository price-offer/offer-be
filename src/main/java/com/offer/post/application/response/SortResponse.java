package com.offer.post.application.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SortResponse {

    private String name;
    private String exposureTitle;

    @Builder
    public SortResponse(String name, String exposureTitle) {
        this.name = name;
        this.exposureTitle = exposureTitle;
    }
}
