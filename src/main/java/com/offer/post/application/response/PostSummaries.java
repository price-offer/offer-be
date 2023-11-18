package com.offer.post.application.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSummaries {

    private List<PostSummary> posts;
    private boolean hasNext;

    @Builder
    public PostSummaries(List<PostSummary> posts, boolean hasNext) {
        this.posts = posts;
        this.hasNext = hasNext;
    }
}
