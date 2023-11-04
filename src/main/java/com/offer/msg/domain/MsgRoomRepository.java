package com.offer.msg.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MsgRoomRepository extends JpaRepository<MsgRoom, Long> {
    @Override
    <S extends MsgRoom> S save(S entity);

    Optional<MsgRoom> findByMember1IdAndMember2IdAndOfferId(Long member1Id, Long member2Id, Long offerId);

    Slice<MsgRoom> findSliceByMember1Id(PageRequest pageRequest, Long member1Id);

    Slice<MsgRoom> findSliceByMember2Id(PageRequest pageRequest, Long member2Id);
}
