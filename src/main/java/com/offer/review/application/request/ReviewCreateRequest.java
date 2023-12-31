package com.offer.review.application.request;

import com.offer.member.Member;
import com.offer.post.domain.Post;
import com.offer.review.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewCreateRequest {
    private Long targetMemberId;

    private Long postId;

    private int score;

    private String content;

    @Builder
    public ReviewCreateRequest(Long targetMemberId, Long postId, int score, String content) {
        this.targetMemberId = targetMemberId;
        this.postId = postId;
        this.score = score;
        this.content = content;
    }

    public Review toEntity(Member reviewee, Member reviewer, Post post, boolean revieweeIsBuyer) {
        return Review.builder()
                .reviewee(reviewee)
                .reviewer(reviewer)
                .post(post)
                .score(score)
                .content(content)
                .revieweeIsBuyer(revieweeIsBuyer)
                .build();
    }
}
