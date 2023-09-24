package com.offer.post.application.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSummaries {

    private List<PostSummary> data;
    private boolean hasNext;

    @Builder
    public PostSummaries(List<PostSummary> data, boolean hasNext) {
        this.data = data;
        this.hasNext = hasNext;
    }
}
