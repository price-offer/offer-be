package com.offer.post.domain;

import com.offer.post.application.response.CategoryResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class CategoryRepository {

    private final List<CategoryResponse> store;

    public CategoryRepository(List<CategoryResponse> store) {
        this.store = store;
        store.addAll(List.of(
            CategoryResponse.builder()
                .name("MAN_FASHION")
                .exposureTitle("남성패션/잡화")
                .build(),
            CategoryResponse.builder()
                .name("WOMAN_FASHION")
                .exposureTitle("여성패션/잡화")
                .build(),
            CategoryResponse.builder()
                .name("GAME")
                .exposureTitle("게임")
                .build()));
    }

    public List<CategoryResponse> getCategories() {
        return new ArrayList<>(store);
    }
}
