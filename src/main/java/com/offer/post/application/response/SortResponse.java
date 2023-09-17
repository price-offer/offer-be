package com.offer.post.application.response;

import com.offer.post.domain.sort.SortItem;
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

    public static SortResponse from(SortItem sortItem) {
        return SortResponse.builder()
            .name(sortItem.getName())
            .exposureTitle(sortItem.getExposureTitle())
            .build();
    }
}
