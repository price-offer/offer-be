package com.offer.msg.application;

import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.common.response.exception.BusinessException;
import com.offer.msg.application.request.MsgCreateRequest;
import com.offer.msg.application.response.MsgInfoResponse;
import com.offer.msg.domain.Msg;
import com.offer.msg.domain.MsgRepository;
import com.offer.msg.domain.MsgRoom;
import com.offer.msg.domain.MsgRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MsgService {
    private final MsgRoomRepository msgRoomRepository;
    private final MsgRepository msgRepository;

    @Transactional
    public CommonCreationResponse sendMsg(Long msgRoomId, MsgCreateRequest request, Long senderId) {
        MsgRoom msgRoom = msgRoomRepository.findById(msgRoomId)
                .orElseThrow(() -> new BusinessException(ResponseMessage.MESSAGE_ROOM_NOT_FOUND));

        Msg msg = msgRepository.save(
                request.toEntity(msgRoom, request.getContent(), senderId)
        );

        return CommonCreationResponse.of(msg.getId(), msg.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<MsgInfoResponse> getMsgs(Long msgRoomId) {
        List<Msg> msgs = msgRepository.findAllByRoomId(msgRoomId);

        msgs.stream()
                .filter(m -> !m.isRead())
                .forEach(m -> {
                    m.markRead();
                    msgRepository.save(m);
                });

        return msgs.stream()
                .map(m -> new MsgInfoResponse(m.getContent(), m.getCreatedAt()))
                .sorted(Comparator.comparing(MsgInfoResponse::getSendTime))
                .collect(Collectors.toList());
    }
}
