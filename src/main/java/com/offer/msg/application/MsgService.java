package com.offer.msg.application;

import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.common.response.exception.BusinessException;
import com.offer.member.MemberRepository;
import com.offer.msg.application.request.MsgCreateRequest;
import com.offer.msg.application.response.MsgInfoResponse;
import com.offer.msg.domain.Msg;
import com.offer.msg.domain.MsgRepository;
import com.offer.msg.domain.MsgRoom;
import com.offer.msg.domain.MsgRoomRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MsgService {
    private final MsgRoomRepository msgRoomRepository;
    private final MsgRepository msgRepository;
    private final MemberRepository memberRepository;

    private final int DEFAULT_SLICE_SIZE;

    public MsgService(MsgRoomRepository msgRoomRepository,
                      MsgRepository msgRepository,
                      MemberRepository memberRepository,
                      @Value("${slice.default-size}") int DEFAULT_SLICE_SIZE) {
        this.msgRoomRepository = msgRoomRepository;
        this.msgRepository = msgRepository;
        this.memberRepository = memberRepository;
        this.DEFAULT_SLICE_SIZE = DEFAULT_SLICE_SIZE;
    }

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
    public List<MsgInfoResponse> getMsgs(int page, Long msgRoomId) {
        PageRequest pageRequest = PageRequest.of(page, DEFAULT_SLICE_SIZE);

        Slice<Msg> msgs = msgRepository.findSliceByRoomId(pageRequest, msgRoomId);

        msgs.stream()
                .filter(m -> !m.isRead())
                .forEach(m -> {
                    m.markRead();
                    msgRepository.save(m);
                });

        return msgs.stream()
                .map(m -> MsgInfoResponse.from(
                        m.getContent(),
                        memberRepository.getById(m.getSenderId()),
                        m.getCreatedAt())
                )
                .sorted(Comparator.comparing(MsgInfoResponse::getSendTime))
                .collect(Collectors.toList());
    }
}
