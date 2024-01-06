package com.offer.msg.application.response;

import com.offer.member.Member;
import com.offer.msg.application.response.MsgRoomInfoResponse.PartnerBriefResponse;
import com.offer.msg.application.response.MsgRoomInfoResponse.PostBriefResponse;
import com.offer.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@Builder
public class MsgRoomBriefResponse {

    private final Long id;

    private final PartnerBriefResponse partner;

    private final PostBriefResponse post;

    private final int offerPrice;

    public static MsgRoomBriefResponse from(Long msgRoomId, Member partner, Post post, int offerPrice) {
        return MsgRoomBriefResponse.builder()
            .id(msgRoomId)
            .partner(new PartnerBriefResponse(partner.getId(), partner.getProfileImageUrl(), partner.getNickname()))
            .post(new PostBriefResponse(post.getId(), post.getThumbnailImageUrl(), post.getPrice()))
            .offerPrice(offerPrice)
            .build();
    }
}
