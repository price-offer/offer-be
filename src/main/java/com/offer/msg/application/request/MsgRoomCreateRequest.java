package com.offer.msg.application.request;

import com.offer.member.Member;
import com.offer.msg.domain.MsgRoom;
import com.offer.offer.domain.Offer;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MsgRoomCreateRequest {

    private Long offerId;

    public MsgRoom toEntity(Member member1, Member member2, Offer offer) {
        return MsgRoom.builder()
                .seller(member1)
                .offerer(member2)
                .offer(offer)
                .build();
    }
}
