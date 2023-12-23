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
    private String imageUrl;

    @Builder
    public CategoryResponse(String code, String name, String imageUrl) {
        this.code = code;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static CategoryResponse from(Category category) {
        return CategoryResponse.builder()
            .code(category.getCode())
            .name(category.getName())
            .imageUrl(category.getImageUrl())
            .build();
    }
}
