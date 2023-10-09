package com.offer.review.application;

import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.common.response.exception.BusinessException;
import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.post.domain.Post;
import com.offer.post.domain.PostRepository;
import com.offer.review.application.request.ReviewCreateRequest;
import com.offer.review.application.response.ReviewInfoResponse;
import com.offer.review.domain.Review;
import com.offer.review.domain.ReviewRepository;
import com.offer.review.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CommonCreationResponse createReview(ReviewCreateRequest request, Long reviewerId) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new BusinessException(ResponseMessage.ARTICLE_NOT_FOUND));

        Member reviewer = memberRepository.getById(reviewerId);
        Member reviewee = memberRepository.getById(request.getTargetMemberId());

        if (reviewRepository.existsByReviewerAndPost(reviewer, post)) {
            throw new BusinessException(ResponseMessage.ALREADY_REVIEWED);
        }

        boolean isRevieweeBuyer = !post.isWriter(reviewer.getId());

        // TODO: update member score

        Review review = reviewRepository.save(
                request.toEntity(reviewee, reviewer, post, isRevieweeBuyer)
        );

        return CommonCreationResponse.of(review.getId(), review.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<ReviewInfoResponse> getReviews(Long memberId, Role role) {

        var reviews = switch (role) {
            case BUYER -> reviewRepository.getAllByRevieweeIdAndIsRevieweeBuyer(memberId, true);
            case SELLER -> reviewRepository.getAllByRevieweeIdAndIsRevieweeBuyer(memberId, false);
            case ALL -> reviewRepository.getAllByRevieweeId(memberId);
        };

        return reviews.stream()
                .filter(Objects::nonNull)
                .map(ReviewInfoResponse::from)
                .collect(Collectors.toList());
    }
}
