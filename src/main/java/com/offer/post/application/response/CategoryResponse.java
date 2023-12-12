package com.offer.post.application.response;

import com.offer.post.domain.category.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponse {

    private String code;
    private String name;

    @Builder
    public CategoryResponse(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
            .code(category.getName())
            .name(category.getExposureTitle())
            .build();
    }
}
