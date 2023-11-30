package com.offer.member;

import com.offer.authentication.application.response.MemberProfileResponse;
import com.offer.authentication.application.response.MemberResponse;
import com.offer.member.request.MemberUpdateRequest;
import com.offer.post.domain.LikeRepository;
import com.offer.post.domain.PostRepository;
import com.offer.post.domain.TradeStatus;
import com.offer.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;
    private final LikeRepository likeRepository;

    public boolean hasNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }


    public MemberResponse getMember(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null");
        }
        Member member = memberRepository.getById(memberId);
        int selling = postRepository.countBySellerAndTradeStatus(member, TradeStatus.SELLING);
        int sold = postRepository.countBySellerAndTradeStatus(member, TradeStatus.SOLD);
        int review = reviewRepository.countByRevieweeIdOrReviewerId(memberId, memberId);
        int like = likeRepository.countByMemberId(memberId);

        return MemberResponse.builder()
            .id(member.getId())
            .profileImageUrl(member.getProfileImageUrl())
            .offerLevel(member.getOfferLevel())
            .nickname(member.getNickname())
            .sellingProductCount(selling)
            .soldProductCount(sold)
            .reviewCount(review)
            .likeProductCount(like)
            .build();
    }

    @Transactional
    public Long updateMember(Long memberId, MemberUpdateRequest request) {
        Member member = memberRepository.getById(memberId);
        String profileImageUrl = request.getProfileImageUrl();
        String nickname = request.getNickname();
        if (StringUtils.hasText(profileImageUrl)) {
            member.changeProfileImageUrl(profileImageUrl);
        }
        if (StringUtils.hasText(nickname)) {
            throw new IllegalArgumentException("닉네임이 비어있음");
        }
        member.changeNickname(nickname);
        return member.getId();
    }

    public MemberProfileResponse getMemberProfile(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null");
        }
        Member member = memberRepository.getById(memberId);
        int selling = postRepository.countBySellerAndTradeStatus(member, TradeStatus.SELLING);
        int sold = postRepository.countBySellerAndTradeStatus(member, TradeStatus.SOLD);
        int review = reviewRepository.countByRevieweeIdOrReviewerId(memberId, memberId);
        return MemberProfileResponse.builder()
            .id(member.getId())
            .nickname(member.getNickname())
            .profileImageUrl(member.getProfileImageUrl())
            .offerLevel(member.getOfferLevel())
            .reviewCount(review)
            .sellingProductCount(selling)
            .soldProductCount(sold)
            .build();
    }
}
