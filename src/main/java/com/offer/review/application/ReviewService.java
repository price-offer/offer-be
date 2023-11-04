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
import com.offer.utils.SliceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    private final int DEFAULT_SLICE_SIZE;

    @Autowired
    public ReviewService(@Value("${slice.default-size}") final int DEFAULT_SLICE_SIZE,
                         ReviewRepository reviewRepository,
                         PostRepository postRepository,
                         MemberRepository memberRepository) {
        this.DEFAULT_SLICE_SIZE = DEFAULT_SLICE_SIZE;
        this.reviewRepository = reviewRepository;
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

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
    public List<ReviewInfoResponse> getReviews(int page, Long memberId, Role role) {
        PageRequest pageRequest = PageRequest.of(SliceUtils.getSliceNumber(page), DEFAULT_SLICE_SIZE);

        Slice<Review> reviews = switch (role) {
            case BUYER -> reviewRepository.findSliceByRevieweeIdAndIsRevieweeBuyer(pageRequest, memberId, true);
            case SELLER -> reviewRepository.findSliceByRevieweeIdAndIsRevieweeBuyer(pageRequest, memberId, false);
            case ALL -> reviewRepository.findSliceByRevieweeId(pageRequest, memberId);
        };

        return reviews.stream()
                .filter(Objects::nonNull)
                .map(ReviewInfoResponse::from)
                .collect(Collectors.toList());
    }
}
