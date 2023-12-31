package com.offer.review.application.response;

import com.offer.member.Member;
import com.offer.review.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@RequiredArgsConstructor
@Builder
public class ReviewInfoResponse {
    private final Long id;

    private final ReviewTargetMemberResponse reviewTargetMember;

    private final int score;

    private final PostBriefResponse post;

    private final String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime createdDate;

    @Getter
    @RequiredArgsConstructor
    public static class ReviewTargetMemberResponse {
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

    public static ReviewInfoResponse buildReviewInfoResponse(Review review, Member member) {
        // 내가 reviewer인 경우 reviewee, 내가 reviewee인 경우 reviewer
        Member reviewer = review.getReviewer();
        if (member.getId().equals(reviewer.getId())) {
            return ReviewInfoResponse.from(review, review.getReviewee());
        }
        return ReviewInfoResponse.from(review, review.getReviewer());
    }

    private static ReviewInfoResponse from(Review review, Member targetMember) {
        return ReviewInfoResponse.builder()
                .id(review.getId())
                .reviewTargetMember(new ReviewTargetMemberResponse(
                    targetMember.getId(),
                    targetMember.getProfileImageUrl(),
                    targetMember.getNickname())
                )
                .score(review.getScore())
                .post(new PostBriefResponse(
                        review.getPost().getId(), review.getPost().getTitle()
                ))
                .content(review.getContent())
                .createdDate(review.getCreatedAt())
                .build();
    }
}
