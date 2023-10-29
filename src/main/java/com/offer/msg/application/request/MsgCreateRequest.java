package com.offer.msg.application.request;

import com.offer.msg.domain.Msg;
import com.offer.msg.domain.MsgRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MsgCreateRequest {
    private String content;

    public Msg toEntity(MsgRoom msgRoom, String content, Long senderId) {
        return Msg.builder()
                .room(msgRoom)
                .senderId(senderId)
                .content(content)
                .build();
    }
}
