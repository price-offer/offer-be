package com.offer.offer.application;

import com.offer.authentication.presentation.LoginMember;
import com.offer.common.response.CommonCreationResponse;
import com.offer.config.Properties;
import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.msg.domain.Msg;
import com.offer.msg.domain.MsgRepository;
import com.offer.msg.domain.MsgRoom;
import com.offer.msg.domain.MsgRoomRepository;
import com.offer.offer.application.request.OfferCreateRequest;
import com.offer.offer.application.response.OfferSummaries;
import com.offer.offer.application.response.OfferSummary;
import com.offer.offer.application.response.OffersResponse;
import com.offer.offer.domain.Offer;
import com.offer.offer.domain.OfferRepository;
import com.offer.post.application.request.OfferReadParams;
import com.offer.post.domain.Post;
import com.offer.post.domain.PostRepository;

import com.offer.review.application.response.ReviewInfoResponse;
import com.offer.review.domain.Review;
import com.offer.review.domain.ReviewRepository;
import java.util.List;

import com.offer.utils.SliceUtils;
import java.util.Optional;
import java.util.stream.Collectors;
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
    private final MsgRoomRepository msgRoomRepository;
    private final MsgRepository msgRepository;
    private final ReviewRepository reviewRepository;

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
        offerRepository.save(offer);

        return CommonCreationResponse.of(offer.getId(), offer.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public OffersResponse getAllOffersByPost(Long postId, Long memberId, String sort) {
        Post post = postRepository.getById(postId);
        List<Offer> offersByPost = offerRepository.findAllByPostOrderByIdDesc(post);

        if (sort != null && sort.equals("PRICE_DESC")) {
            offersByPost = offerRepository.findAllByPostOrderByPriceDesc(post);
        }

        int offerCount = 0;
        if (memberId != null) {
            Member member = memberRepository.getById(memberId);
            offerCount = offerRepository.countByPostAndOfferer(post, member);
        }

        return OffersResponse.of(offersByPost.stream().toList(), postId, offerCount);
    }

    @Transactional(readOnly = true)
    public OfferSummaries getAllOffersByMember(OfferReadParams params, LoginMember loginMember) {
        if (loginMember.getId() == null) {
            throw new IllegalArgumentException("잘못된 토큰입니다");
        }

        Member offerer = memberRepository.getById(loginMember.getId());
        List<Offer> offers = offerRepository.findAllByOfferer(offerer);

        if (offers.size() > params.getLimit()) {
            offers.remove(params.getLimit());
            return OfferSummaries.builder()
                .offers(offers.stream()
                    .map(this::getOfferSummary)
                    .collect(Collectors.toList()))
                .hasNext(true)
                .build();
        }


        return OfferSummaries.builder()
            .offers(offers.stream()
                .map(this::getOfferSummary)
                .collect(Collectors.toList()))
            .hasNext(false)
            .build();
    }

    private OfferSummary getOfferSummary(Offer offer) {
        // 내가 보낸 offer에 대한 내가 보낸 후기
        Optional<MsgRoom> msgRoom = msgRoomRepository.findByOffererAndOffer(
            offer.getOfferer(), offer);

        if (msgRoom.isEmpty()) {
            return OfferSummary.from(offer);
        }

        MsgRoom room = msgRoom.get();
        Member seller = room.getSeller();
        List<Msg> messages = msgRepository.findAllByRoomAndSenderId(room, seller.getId());

        boolean reviewAvailable = !messages.isEmpty();
        ReviewInfoResponse review = reviewRepository.findByPostAndReviewer(offer.getPost(), offer.getOfferer())
            .map(ReviewInfoResponse::from)
            .orElse(null);

        return OfferSummary.from(offer, reviewAvailable, review);
    }
}
