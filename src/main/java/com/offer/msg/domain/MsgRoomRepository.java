package com.offer.msg.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MsgRoomRepository extends JpaRepository<MsgRoom, Long> {
    @Override
    <S extends MsgRoom> S save(S entity);

    Optional<MsgRoom> findByMember1IdAndMember2IdAndOfferId(Long member1Id, Long member2Id, Long offerId);

    List<MsgRoom> getAllByMember1Id(Long member1Id);

    List<MsgRoom> getAllByMember2Id(Long member2Id);
}
