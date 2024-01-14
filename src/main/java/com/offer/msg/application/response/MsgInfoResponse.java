package com.offer.msg.application.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.offer.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MsgInfoResponse {

    private final Long id;
    private final String content;

    private final PartnerBriefResponse member;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
    private final LocalDateTime sendTime;

    @Getter
    @RequiredArgsConstructor
    public static class PartnerBriefResponse {
        private final Long id;

        private final String imageUrl;

        private final String nickname;
    }

    public static MsgInfoResponse from(Long id, String content, Member member, LocalDateTime sendTime) {
        return new MsgInfoResponse(
                id,
                content,
                new PartnerBriefResponse(member.getId(), member.getProfileImageUrl(), member.getNickname()),
                sendTime
        );
    }
}
