package com.offer.post.application.response;

import com.offer.post.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponse {

    private String name;
    private String exposureTitle;

    @Builder
    public CategoryResponse(String name, String exposureTitle) {
        this.name = name;
        this.exposureTitle = exposureTitle;
    }

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
            .name(category.getName())
            .exposureTitle(category.getExposureTitle())
            .build();
    }
}
