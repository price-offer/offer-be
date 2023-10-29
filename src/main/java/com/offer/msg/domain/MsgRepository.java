package com.offer.msg.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MsgRepository extends JpaRepository<Msg, Long> {
    @Override
    <S extends Msg> S save(S entity);

    List<Msg> findAllByRoomId(Long roomId);
}
