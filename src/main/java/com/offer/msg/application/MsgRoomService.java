package com.offer.msg.application;

import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.common.response.exception.BusinessException;
import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.msg.application.request.MsgRoomCreateRequest;
import com.offer.msg.application.response.MsgRoomInfoResponse;
import com.offer.msg.domain.MsgRoom;
import com.offer.msg.domain.MsgRoomRepository;
import com.offer.offer.domain.Offer;
import com.offer.offer.domain.OfferRepository;
import com.offer.post.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MsgRoomService {
    private final MsgRoomRepository msgRoomRepository;
    private final MemberRepository memberRepository;
    private final OfferRepository offerRepository;

    private final int DEFAULT_SLICE_SIZE;

    @Autowired
    public MsgRoomService(MsgRoomRepository msgRoomRepository,
                          MemberRepository memberRepository,
                          OfferRepository offerRepository,
                          @Value("${slice.default-size}") int DEFAULT_SLICE_SIZE) {
        this.msgRoomRepository = msgRoomRepository;
        this.memberRepository = memberRepository;
        this.offerRepository = offerRepository;
        this.DEFAULT_SLICE_SIZE = DEFAULT_SLICE_SIZE;
    }

    @Transactional
    public CommonCreationResponse createMsgRoom(MsgRoomCreateRequest request, Long memberId) {
        Long targetMemberId = request.getTargetMemberId();
        Long offerId = request.getOfferId();

        validateAlreadyExistRoom(memberId, targetMemberId, offerId);

        Member member1 = memberRepository.getById(memberId);
        Member member2 = memberRepository.getById(targetMemberId);
        Offer offer = offerRepository.getById(offerId);

        MsgRoom msgRoom = msgRoomRepository.save(
                request.toEntity(member1, member2, offer)
        );

        return CommonCreationResponse.of(msgRoom.getId(), msgRoom.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<MsgRoomInfoResponse> getMsgRooms(int page, Long memberId) {
        PageRequest pageRequest = PageRequest.of(page, DEFAULT_SLICE_SIZE);

        Slice<MsgRoom> msgRooms1 = msgRoomRepository.findSliceByMember1Id(pageRequest, memberId);
        Slice<MsgRoom> msgRooms2 = msgRoomRepository.findSliceByMember2Id(pageRequest, memberId);

        List<MsgRoomInfoResponse> response = new ArrayList<>(msgRooms1.getNumber() + msgRooms2.getNumber());

        for (MsgRoom msgRoom : msgRooms1) {  // TODO: refactor
            Member partner = msgRoom.getMember2();
            String lastContent = "tmp_lastContent";
            int offerPrice = (msgRoom.getOffer() == null) ? -1 : msgRoom.getOffer().getPrice();
            LocalDateTime lastSendTime = LocalDateTime.now();
            Post post = msgRoom.getOffer().getPost();
            int notReadCnt = 3;

            MsgRoomInfoResponse tmpResponse = MsgRoomInfoResponse
                    .from(msgRoom, partner, lastContent, offerPrice, lastSendTime, post, notReadCnt);

            response.add(tmpResponse);
        }

        for (MsgRoom msgRoom : msgRooms2) {
            Member partner = msgRoom.getMember1();
            String lastContent = "tmp_lastContent";
            int offerPrice = (msgRoom.getOffer() == null) ? -1 : msgRoom.getOffer().getPrice();
            LocalDateTime lastSendTime = LocalDateTime.now();
            Post post = msgRoom.getOffer().getPost();
            int notReadCnt = 2;

            MsgRoomInfoResponse tmpResponse = MsgRoomInfoResponse
                    .from(msgRoom, partner, lastContent, offerPrice, lastSendTime, post, notReadCnt);

            response.add(tmpResponse);
        }

        return response;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void validateAlreadyExistRoom(Long member1Id, Long member2Id, Long offerId) {
        Optional<MsgRoom> opt1 = msgRoomRepository
                .findByMember1IdAndMember2IdAndOfferId(member1Id, member2Id, offerId);
        Optional<MsgRoom> opt2 = msgRoomRepository
                .findByMember1IdAndMember2IdAndOfferId(member2Id, member1Id, offerId);

        if (opt1.isEmpty() && opt2.isEmpty()) return;

        throw new BusinessException(ResponseMessage.ALREADY_EXIST_MSG_ROOM);
    }
}
