package com.offer.post.domain;

import com.offer.post.application.response.SortResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class SortItemRepository {

    private Map<SortType, List<SortResponse>> store = new HashMap<>();

    public SortItemRepository() {
        SortResponse recentItem = SortResponse.builder()
            .name("RECENT_CREATED")
            .exposureTitle("최신순")
            .build();
        SortResponse lowPrice = SortResponse.builder()
            .name("LOW_PRICE")
            .exposureTitle("낮은 가격순")
            .build();
        SortResponse highPrice = SortResponse.builder()
            .name("HIGH_PRICE")
            .exposureTitle("높은 가격순")
            .build();
        this.store.put(SortType.POST, List.of(recentItem, lowPrice));
        this.store.put(SortType.OFFER, List.of(highPrice, recentItem));
    }

    public List<SortResponse> get(SortType type) {
        return store.get(type);
    }
}
