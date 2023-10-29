package com.offer.msg.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.offer.member.Member;
import com.offer.msg.domain.MsgRoom;
import com.offer.post.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@Builder
public class MsgRoomInfoResponse {
    private final Long id;

    private final PartnerBriefResponse partner;

    private final PostBriefResponse post;

    private final int offerPrice;

    private final String lastContent;

    private final int notReadCnt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime lastSendTime;

    @Getter
    @RequiredArgsConstructor
    public static class PartnerBriefResponse {
        private final Long id;

        private final String imageUrl;

        private final String nickname;
    }

    @Getter
    @RequiredArgsConstructor
    public static class PostBriefResponse {
        private final Long id;

        private final String imageUrl;
    }

    public static MsgRoomInfoResponse from(MsgRoom msgRoom, Member partner, String lastContent, int offerPrice,
                                           LocalDateTime lastSendTime, Post post, int notReadCnt) {
        return MsgRoomInfoResponse.builder()
                .id(msgRoom.getId())
                .partner(new PartnerBriefResponse(partner.getId(), partner.getProfileImageUrl(), partner.getNickname()))
                .post(new PostBriefResponse(post.getId(), post.getThumbnailImageUrl()))
                .offerPrice(offerPrice)
                .lastContent(lastContent)
                .lastSendTime(lastSendTime)
                .notReadCnt(notReadCnt)
                .build();
    }
}
