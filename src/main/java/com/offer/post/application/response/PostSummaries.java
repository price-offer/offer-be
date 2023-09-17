package com.offer.post.application.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostSummaries {

    private List<PostSummary> data;
    // TODO: 2023/09/17 페이징 정보

    @Builder
    public PostSummaries(List<PostSummary> data) {
        this.data = data;
    }
}
