package com.offer.msg.domain;

import com.offer.member.Member;
import com.offer.offer.domain.Offer;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MsgRoomRepository extends JpaRepository<MsgRoom, Long> {
    @Override
    <S extends MsgRoom> S save(S entity);

    Optional<MsgRoom> findBySellerIdAndOffererIdAndOfferId(Long sellerId, Long offererId, Long offerId);

    Slice<MsgRoom> findSliceBySellerId(PageRequest pageRequest, Long sellerId);

    Slice<MsgRoom> findSliceByOffererId(PageRequest pageRequest, Long offererId);

    Optional<MsgRoom> findByOffererAndOffer(Member offerer, Offer offer);
}
