package com.offer.offer.application;

import com.offer.common.response.CommonCreationResponse;
import com.offer.config.Properties;
import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.offer.application.request.OfferCreateRequest;
import com.offer.offer.application.response.OffersResponse;
import com.offer.offer.domain.Offer;
import com.offer.offer.domain.OfferRepository;
import com.offer.post.domain.Post;
import com.offer.post.domain.PostRepository;

import java.util.List;

import com.offer.utils.SliceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OfferService {
    private final PostRepository postRepository;
    private final OfferRepository offerRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CommonCreationResponse createOffer(Long postId, OfferCreateRequest request, Long offererId) {
        Member offerer = memberRepository.getById(offererId);
        List<Offer> offersByOfferer = offerRepository.findAllByOffererIdAndPostId(offererId, postId);
        int offerCountOfCurrentMember = offersByOfferer.size();

        if (offerCountOfCurrentMember >= Properties.MAX_OFFER_COUNT) {
            throw new IllegalArgumentException(
                    "최대 가격제안 횟수 초과. offerCountOfCurrentMember = " + offerCountOfCurrentMember);
        }

        if (offerCountOfCurrentMember == 1) {
            Offer prevOffer = offersByOfferer.get(0);
            if (prevOffer.getPrice().intValue() == request.getPrice()) {
                throw new IllegalArgumentException("이전 가격제안 가격과 동일한 가격제안");
            }
        }

        Post post = postRepository.getById(postId);

        Offer offer = request.toEntity(offerer, post);

        return CommonCreationResponse.of(offer.getId(), offer.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public OffersResponse getAllOffersByPost(int page, Long postId) {
        PageRequest pageRequest = PageRequest.of(SliceUtils.getSliceNumber(page), Properties.DEFAULT_SLICE_SIZE);

        Slice<Offer> offersByPost = offerRepository.findSliceByPostId(pageRequest, postId);
        return OffersResponse.of(offersByPost.stream().toList(), postId, 0);
    }

}
