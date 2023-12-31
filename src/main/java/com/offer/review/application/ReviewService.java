package com.offer.review.application;

import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.common.response.exception.BusinessException;
import com.offer.config.Properties;
import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.post.domain.Post;
import com.offer.post.domain.PostRepository;
import com.offer.review.application.request.ReviewCreateRequest;
import com.offer.review.application.response.ReviewInfoResponse;
import com.offer.review.application.response.ReviewInfoResponse.ReviewTargetMemberResponse;
import com.offer.review.application.response.ReviewInfoResponses;
import com.offer.review.domain.Review;
import com.offer.review.domain.ReviewRepository;
import com.offer.review.domain.Role;
import com.offer.utils.SliceUtils;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.spi.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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
                .orElseThrow(() -> new BusinessException(ResponseMessage.POST_NOT_FOUND));

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
    public ReviewInfoResponses getReviews(Long memberId, Role role, Long lastId, int limit) {

        Member member = memberRepository.getById(memberId);

        List<Review> result = new ArrayList<>();

        if (role == Role.ALL) {
            result = reviewRepository.findTop10ByReviewerOrRevieweeAndIdGreaterThanEqual(member, member, lastId);
        }
        if (role == Role.SELLER) {
            result = reviewRepository.findTop10ByReviewerAndRevieweeIsBuyerAndIdGreaterThanEqual(member, true, lastId);
        }
        if (role == Role.BUYER) {
            result = reviewRepository.findTop10ByRevieweeAndRevieweeIsBuyerAndIdGreaterThanEqual(member, false, lastId);
        }

        if (result.size() > limit) {
            result.remove(limit);
            return ReviewInfoResponses.builder()
                .reviews(result.stream()
                    .map(review -> ReviewInfoResponse.buildReviewInfoResponse(review, member))
                    .collect(Collectors.toList()))
                .hasNext(true)
                .build();
        }

        return ReviewInfoResponses.builder()
            .reviews(result.stream()
                .map(review -> ReviewInfoResponse.buildReviewInfoResponse(review, member))
                .collect(Collectors.toList()))
            .hasNext(false)
            .build();
    }
}
