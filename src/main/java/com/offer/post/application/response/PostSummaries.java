package com.offer.post.application.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSummaries {

    private Integer totalCount;
    private List<PostSummary> posts;
    private boolean hasNext;

    @Builder
    public PostSummaries(Integer totalCount, List<PostSummary> posts, boolean hasNext) {
        this.totalCount = totalCount;
        this.posts = posts;
        this.hasNext = hasNext;
    }
}
