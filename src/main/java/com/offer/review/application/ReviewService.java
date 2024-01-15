package com.offer.review.application;

import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.common.response.exception.BusinessException;
import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.post.domain.Post;
import com.offer.post.domain.PostRepository;
import com.offer.review.application.request.ReviewCreateRequest;
import com.offer.review.application.response.ReviewCountResponse;
import com.offer.review.application.response.ReviewInfoResponse;
import com.offer.review.application.response.ReviewInfoResponses;
import com.offer.review.domain.Review;
import com.offer.review.domain.ReviewRepository;
import com.offer.review.domain.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CommonCreationResponse createReview(ReviewCreateRequest request, Long reviewerId) {
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("post가 존재하지 않습니다. postId = " + request.getPostId()));

        Member reviewer = memberRepository.getById(reviewerId);
        Member reviewee = memberRepository.getById(request.getTargetMemberId());

        if (reviewRepository.existsByReviewerAndPost(reviewer, post)) {
            throw new IllegalArgumentException("이미 리뷰를 작성했습니다. reviewer = " + reviewer + ", post = " + post);
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

        if (role == Role.ALL) { // 전체 받은 후기
            result = reviewRepository.findTop10ByRevieweeAndIdGreaterThanEqual(member, lastId);
        }
        if (role == Role.SELLER) { // 판매자에게 받은 후기
            result = reviewRepository.findTop10ByRevieweeAndRevieweeIsBuyerAndIdGreaterThanEqual(member, true, lastId);
        }
        if (role == Role.BUYER) { // 구매자에게 받은 후기
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

    @Transactional(readOnly = true)
    public ReviewCountResponse getReviewCounts(Long memberId) {

        Member member = memberRepository.getById(memberId);
        int all = reviewRepository.countByRevieweeId(memberId);
        int asSeller = reviewRepository.countByRevieweeAndRevieweeIsBuyer(member, true);
        int asBuyer = reviewRepository.countByRevieweeAndRevieweeIsBuyer(member, false);

        return new ReviewCountResponse(all, asSeller, asBuyer);
    }
}
