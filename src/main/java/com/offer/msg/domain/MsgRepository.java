package com.offer.msg.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MsgRepository extends JpaRepository<Msg, Long> {
    @Override
    <S extends Msg> S save(S entity);

    Slice<Msg> findSliceByRoomId(PageRequest pageRequest, Long roomId);
}
