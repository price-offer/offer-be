package com.offer.review.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.offer.review.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class ReviewInfoResponse {
    private final Long id;

    private final ReviewerBriefResponse reviewer;

    private final int score;

    private final PostBriefResponse post;

    private final String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime createdDate;

    @Getter
    @RequiredArgsConstructor
    public static class ReviewerBriefResponse {
        private final Long id;

        private final String profileImageUrl;

        private final String nickname;
    }

    @Getter
    @RequiredArgsConstructor
    public static class PostBriefResponse {
        private final Long id;

        private final String title;
    }

    public static ReviewInfoResponse from(Review review) {
        return ReviewInfoResponse.builder()
                .id(review.getId())
                .reviewer(new ReviewerBriefResponse(
                        review.getReviewer().getId(),
                        review.getReviewer().getProfileImageUrl(),
                        review.getReviewer().getNickname())
                )
                .score(review.getScore())
                .post(new PostBriefResponse(
                        review.getPost().getId(), review.getPost().getTitle()
                ))
                .content(review.getContent())
                .build();
    }
}
