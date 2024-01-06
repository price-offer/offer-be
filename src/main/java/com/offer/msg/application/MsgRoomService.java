package com.offer.msg.application;

import com.offer.common.response.CommonCreationResponse;
import com.offer.common.response.ResponseMessage;
import com.offer.common.response.exception.BusinessException;
import com.offer.config.Properties;
import com.offer.member.Member;
import com.offer.member.MemberRepository;
import com.offer.msg.application.request.MsgRoomCreateRequest;
import com.offer.msg.application.response.MsgRoomBriefResponse;
import com.offer.msg.application.response.MsgRoomInfoResponse;
import com.offer.msg.domain.MsgRoom;
import com.offer.msg.domain.MsgRoomRepository;
import com.offer.offer.domain.Offer;
import com.offer.offer.domain.OfferRepository;
import com.offer.post.domain.Post;
import com.offer.utils.SliceUtils;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class MsgRoomService {
    private final MsgRoomRepository msgRoomRepository;
    private final MemberRepository memberRepository;
    private final OfferRepository offerRepository;

    @Transactional
    public CommonCreationResponse createMsgRoom(MsgRoomCreateRequest request, Long memberId) {
        Long offerId = request.getOfferId();

        validateAlreadyExistRoom(offerId);

        Offer offer = offerRepository.getById(offerId);
        Member seller = memberRepository.getById(memberId);
        Member offerer = offer.getOfferer();

        MsgRoom msgRoom = msgRoomRepository.save(
                request.toEntity(seller, offerer, offer)
        );

        return CommonCreationResponse.of(msgRoom.getId(), msgRoom.getCreatedAt());
    }

    @Transactional(readOnly = true)
    public List<MsgRoomInfoResponse> getMsgRooms(int page, Long memberId) {
        PageRequest pageRequest = PageRequest.of(SliceUtils.getSliceNumber(page), Properties.DEFAULT_SLICE_SIZE);

        Slice<MsgRoom> msgRooms1 = msgRoomRepository.findSliceBySellerId(pageRequest, memberId);
        Slice<MsgRoom> msgRooms2 = msgRoomRepository.findSliceByOffererId(pageRequest, memberId);

        List<MsgRoomInfoResponse> response = new ArrayList<>(msgRooms1.getNumber() + msgRooms2.getNumber());

        for (MsgRoom msgRoom : msgRooms1) {  // TODO: refactor
            Member partner = msgRoom.getOfferer();
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
            Member partner = msgRoom.getSeller();
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

    @Transactional(readOnly = true)
    public MsgRoomBriefResponse getMsgRoom(Long roomId, Long memberId) {
        MsgRoom msgRoom = msgRoomRepository.findById(roomId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 roomId = " + roomId));

        Member member = memberRepository.getById(memberId);
        Member partner = null;
        if (msgRoom.getSeller().getId().equals(member.getId())) {
            partner = msgRoom.getOfferer();
        } else if (msgRoom.getOfferer().getId().equals(member.getId())) {
            partner = msgRoom.getSeller();
        } else {
            throw new IllegalArgumentException("해당 메시지룸에 사용자가 참여하고 있지 않습니다. roomId = " + roomId + ", memberId = " + memberId);
        }

        return MsgRoomBriefResponse.from(msgRoom.getId(), partner, msgRoom.getOffer().getPost(), msgRoom.getOffer().getPrice());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void validateAlreadyExistRoom(Long offerId) {
        Optional<MsgRoom> msgRoom = msgRoomRepository.findByOfferId(offerId);
        if (msgRoom.isEmpty()) {
            return;
        }
        throw new BusinessException(ResponseMessage.ALREADY_EXIST_MSG_ROOM);
    }
}
